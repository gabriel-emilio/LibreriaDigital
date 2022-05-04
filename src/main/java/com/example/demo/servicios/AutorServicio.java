
package com.example.demo.servicios;

import com.example.demo.entidades.Autor;
import com.example.demo.excepciones.ExcepcionPropia;
import com.example.demo.repositorio.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
@Autowired
private AutorRepositorio autorRepositorio;    

    public void validar(String nombre) throws ExcepcionPropia{
    
     if(nombre==null || nombre.isEmpty()) {
     throw new ExcepcionPropia("El nombre del autor no puede ser nulo");   
}

}

    @Transactional (propagation= Propagation.NESTED)
    public Autor guardar(String nombre, Boolean alta) throws ExcepcionPropia{
      
     validar(nombre); //ponemos validar crtl+ barra espacio para llamar al metodo con sus atributos
     Autor autor= new Autor();
     
     autor.setNombre(nombre);
     autor.setAlta(Boolean.TRUE);
     
     return autorRepositorio.save(autor);
     
 }
    
    @Transactional (propagation= Propagation.NESTED) 
    public void modificar (String id, String nombre) throws ExcepcionPropia{
     validar(nombre);   
     Optional <Autor> optional = autorRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y  realiza las modificaciones, sino devuelve una excepcion
        
     if (optional.isPresent()){
     Autor autor = optional.get();    
     autor.setNombre(nombre);
     autorRepositorio.save(autor); 
 }else{
         throw new ExcepcionPropia ("El id ingresado no corresponde a un autor");
     }

}
    
    @Transactional (readOnly= true) // en este caso solo hacemos una transaccion con la base de datos que es cuando consultamos los datos que tenemos en ella y los mostramos
    public List <Autor> mostrarTodos(){
    return autorRepositorio.findAll(); // este es un metodo heredado del "dao" de repositorio que extendimos en los repo, por eso podemos acceder a el
    }
    
    @Transactional (propagation= Propagation.NESTED) //metodo para borrar, en este caso usamos esta transaccion porque podemos ver que el metodo debe buscar el objeto y ademas borrarlo de la base de datos, es decir realiza dos cosas
    public void borrarPorId(String id) throws ExcepcionPropia{
        
        Optional <Autor> optional = autorRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y lo borra, sino devuelve una excepcion
        
        if (optional.isPresent()){
            autorRepositorio.delete(optional.get());
        }else {
            throw new ExcepcionPropia ("No se ha encontrado el autor");
        }
    }
    
    @Transactional (propagation= Propagation.NESTED)
    public void deshabilitar (String id) throws ExcepcionPropia{ // con este metodo deshabilitamos o le damos de baja a un usuario de la base de datos
        
       Optional <Autor> optional = autorRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y lo borra, sino devuelve una excepcion
        
        if (optional.isPresent()){
            Autor autor= optional.get();
            autor.setAlta(Boolean.FALSE);
            autorRepositorio.save(autor);
        }else{
            throw new ExcepcionPropia ("El autor que se quiere deshabilitar no existe");
        }
    }
    
    @Transactional (readOnly= true)
    public Autor consultar (String id) throws ExcepcionPropia{
         
    Optional <Autor> optional = autorRepositorio.findById(id);
    
    if(optional.isPresent()){
        
        return optional.get(); // con esto mostramos el autor según el id si esque está presente
    }else {
        throw new ExcepcionPropia ("El autor con el id ingresado no existe");
    }
         
     }
    
    public Optional<Autor> buscarPorId(String id) { //metodo para buscar por id
        return autorRepositorio.findById(id);
    }

    
    @Transactional (propagation= Propagation.NESTED)
    public void habilitar (String id) throws ExcepcionPropia{ // con este metodo habilitamos o le damos de baja a un usuario de la base de datos
        
       Optional <Autor> optional = autorRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y lo borra, sino devuelve una excepcion
        
        if (optional.isPresent()){
            Autor autor= optional.get();
            autor.setAlta(Boolean.TRUE);
            autorRepositorio.save(autor);
        }else{
            throw new ExcepcionPropia ("El autor que se quiere habilitar no existe");
        }
    }
}