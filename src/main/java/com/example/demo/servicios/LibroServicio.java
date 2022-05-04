
package com.example.demo.servicios;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Editorial;
import com.example.demo.entidades.Libro;
import com.example.demo.excepciones.ExcepcionPropia;
import com.example.demo.repositorio.LibroRepositorio;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation; // este tenemos que importar para propagation
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
 
 @Autowired
 private LibroRepositorio libroRepositorio;
 
    @Transactional (propagation= Propagation.NESTED)
    public Libro guardar(Long isbn, String titulo, String anio, Integer ejemplares, Integer ejemplaresPrestados,  Boolean alta, Autor autor, Editorial editorial) throws ExcepcionPropia{
     
     
     validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, alta, autor, editorial); //llamamos al metodo de validacion , ponemos validar +crtl+barraespacio
     Libro libro = new Libro(); // inicializamos el objeto libro
     
     libro.setAutor(autor);
     libro.setEditorial(editorial);
     libro.setIsbn(isbn);
     libro.setTitulo(titulo);
     libro.setAnio(anio);
     libro.setEjemplares(ejemplares);
     libro.setEjemplaresPrestados(ejemplaresPrestados);
     libro.setEjemplaresRestantes(ejemplares-ejemplaresPrestados);
     libro.setAlta(Boolean.TRUE);
     
     return libroRepositorio.save(libro);
}
 
    public void validar (Long isbn, String titulo, String anio, Integer ejemplares, Integer ejemplaresPrestados,  Boolean alta, Autor autor, Editorial editorial) throws ExcepcionPropia{
    
     if(isbn==null || isbn.toString().isEmpty()) {
         throw new ExcepcionPropia("El isbn del libro no puede ser nulo");
     }
     if(editorial==null || editorial.toString().trim().isEmpty()) {
         throw new ExcepcionPropia("El libro debe contener una editorial");
     }
      if(autor==null || autor.toString().trim().isEmpty()) {
         throw new ExcepcionPropia ("El autor no puede ser nulo");
     }
      if(titulo==null || titulo.isEmpty()) {
         throw new ExcepcionPropia ("El libro debe contener un titulo");
     } 
      if(anio==null || anio.isEmpty()) {
         throw new ExcepcionPropia ("El libro debe contener un año de publicación");
      }       
     if(ejemplares==null || ejemplares.toString().isEmpty()) {
         throw new ExcepcionPropia ("Debe ingresar la cantidad de Ejmplares");
     } 
     if(ejemplaresPrestados==null || ejemplaresPrestados.toString().isEmpty()) {
         throw new ExcepcionPropia ("Debe ingresar la cantidad de ejemplares prestados");
     } 
     
    
     // ATAJO shift+ ctrl +click y vamos seleccionando lo que queremos y luego lo modificamos a todos juntos ATAJO
     // ATAJO PARA IMPORTAR ctrl+shift+i   **** ATAJO PARA IMPORTAR******
     
}
   
    @Transactional (propagation= Propagation.NESTED) //METODO DE MODIFICACION DE REGISTRO
    public void modificar(String id, Long isbn, String titulo, String anio, Integer ejemplares, Integer ejemplaresPrestados,  Boolean alta, Autor autor, Editorial editorial) throws ExcepcionPropia{
       
      validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados,  alta, autor, editorial);
       Optional <Libro> optional = libroRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y  realiza las modificaciones, sino devuelve una excepcion
        
      if (optional.isPresent()){
      Libro libro = optional.get();    
      libro.setAutor(autor);
      libro.setEditorial(editorial);
      libro.setIsbn(isbn);
      libro.setTitulo(titulo);
      libro.setAnio(anio);
      libro.setEjemplares(ejemplares);
      libro.setEjemplaresPrestados(ejemplaresPrestados);
      libro.setEjemplaresRestantes(libro.getEjemplares()-libro.getEjemplaresPrestados());
      
      
      libroRepositorio.save(libro);  // volvemos a poner save y en este caso como buscamos un libro existente por id, lo va a guardar con los datos modificados
        } else{
          throw new ExcepcionPropia ("No se encontró el libro solicitado");
      }
       
     }
 
 
    @Transactional (readOnly= true) // en este caso solo hacemos una transaccion con la base de datos que es cuando consultamos los datos que tenemos en ella y los mostramos
    public List <Libro> mostrarTodos(){
    return libroRepositorio.findAll(); // este es un metodo heredado del "dao" de repositorio que extendimos en los repo, por eso podemos acceder a el
    }
    
    @Transactional (propagation= Propagation.NESTED) //metodo para borrar, en este caso usamos esta transaccion porque podemos ver que el metodo debe buscar el objeto y ademas borrarlo de la base de datos, es decir realiza dos cosas
    public void borrarPorId(String id) throws ExcepcionPropia{
        
        Optional <Libro> optional = libroRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y lo borra, sino devuelve una excepcion
        
        if (optional.isPresent()){
            libroRepositorio.delete(optional.get());
        }else {
            throw new ExcepcionPropia ("NO se encontró el libro");
        }
    }
    
    @Transactional (propagation= Propagation.NESTED)
    public void deshabilitar (String id) throws ExcepcionPropia{ // con este metodo deshabilitamos o le damos de baja a un usuario de la base de datos
        
       Optional <Libro> optional = libroRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y lo borra, sino devuelve una excepcion
        
        if (optional.isPresent()){
            Libro libro= optional.get();
            libro.setAlta(Boolean.FALSE);
            libroRepositorio.save(libro);
        }else{
            throw new ExcepcionPropia ("El libro que se quiere deshabilitar no existe");
        }
    }
    
    @Transactional (readOnly= true)
    public Libro consultar (String id) throws ExcepcionPropia{
         
    Optional <Libro> optional = libroRepositorio.findById(id);
    
    if(optional.isPresent()){
        
        return optional.get(); // con esto mostramos el libro segun el id si esque esta presente
    }else {
        throw new ExcepcionPropia ("El libro con el id ingresado no existe");
    }
   
      
     }
    
         
    public Optional<Libro> buscarPorId(String id) { //metodo para buscar por id
        return libroRepositorio.findById(id);
    }
    
    @Transactional (propagation= Propagation.NESTED)
    public void habilitar (String id) throws ExcepcionPropia{ // con este metodo habilitamos o le damos de baja a un usuario de la base de datos
        
       Optional <Libro> optional = libroRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y lo borra, sino devuelve una excepcion
        
        if (optional.isPresent()){
            Libro libro= optional.get();
            libro.setAlta(Boolean.TRUE);
            libroRepositorio.save(libro);
        }else{
            throw new ExcepcionPropia ("El libro que se quiere habilitar no existe");
        }
    }

    public void guardar(Long bn, String titulo, String anio, Integer ejemplares, String ejemplaresPrestados, String ejemplaresRestantes, boolean b, Autor autorObjeto, Editorial editorialObjeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
