
package com.example.demo.servicios;


import com.example.demo.entidades.Editorial;
import com.example.demo.excepciones.ExcepcionPropia;
import com.example.demo.repositorio.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
@Autowired   
private EditorialRepositorio editorialRepositorio;

    public void validar(String nombre) throws ExcepcionPropia{
    
     if(nombre==null || nombre.isEmpty()) {
     throw new ExcepcionPropia("El nombre del editorial no puede ser nulo");   
}
 }
     
    @Transactional (propagation= Propagation.NESTED)
    public Editorial guardar(String nombre, Boolean alta) throws ExcepcionPropia{
   
     validar(nombre);
     Editorial editorial= new Editorial();
     
     editorial.setNombre(nombre);
     editorial.setAlta(Boolean.TRUE);
     
     return editorialRepositorio.save(editorial);
     
 }
     
     
    @Transactional (propagation= Propagation.NESTED) 
    public void modificar (String id, String nombre) throws ExcepcionPropia{
     validar(nombre);   
     Optional <Editorial> optional = editorialRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y  realiza las modificaciones, sino devuelve una excepcion
        
     if (optional.isPresent()){
     Editorial editorial = optional.get();    
     editorial.setNombre(nombre);
     editorialRepositorio.save(editorial); 
 }else{
         throw new ExcepcionPropia ("El id ingresado no corresponde a un autor");
     }
}
    
    
    @Transactional (readOnly= true) // en este caso solo hacemos una transaccion con la base de datos que es cuando consultamos los datos que tenemos en ella y los mostramos
    public List <Editorial> mostrarTodos(){
    return editorialRepositorio.findAll(); // este es un metodo heredado del "dao" de repositorio que extendimos en los repo, por eso podemos acceder a el
    }
    
    @Transactional (propagation= Propagation.NESTED) //metodo para borrar, en este caso usamos esta transaccion porque podemos ver que el metodo debe buscar el objeto y ademas borrarlo de la base de datos, es decir realiza dos cosas
    public void borrarPorId(String id) throws ExcepcionPropia{
        
        Optional <Editorial> optional = editorialRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y lo borra, sino devuelve una excepcion
        
        if (optional.isPresent()){
            editorialRepositorio.delete(optional.get());
        }else {
            throw new ExcepcionPropia ("No se ha encontrado el autor");
        }
    }
    
     @Transactional (propagation= Propagation.NESTED)
    public void deshabilitar (String id) throws ExcepcionPropia{ // con este metodo deshabilitamos o le damos de baja a un usuario de la base de datos
        
       Optional <Editorial> optional = editorialRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y lo borra, sino devuelve una excepcion
        
        if (optional.isPresent()){
            Editorial editorial= optional.get();
            editorial.setAlta(Boolean.FALSE);
            editorialRepositorio.save(editorial);
        }else{
            throw new ExcepcionPropia ("El editorial que se quiere deshabilitar no existe");
        }
    }
    
    @Transactional (readOnly= true)
    public Editorial consultar (String id) throws ExcepcionPropia{
         
    Optional <Editorial> optional = editorialRepositorio.findById(id);
    
    if(optional.isPresent()){
        
        return  optional.get(); // con esto mostramos el libro segun el id si esque esta presente
    }else {
        throw new ExcepcionPropia ("El editorial con el id ingresado no existe");
    }
         
     }
    
      public Optional<Editorial> buscarPorId(String id) { //metodo para buscar por id
        return editorialRepositorio.findById(id);
    }
    
    @Transactional (propagation= Propagation.NESTED)
    public void habilitar (String id) throws ExcepcionPropia{ // con este metodo habilitamos o le damos de baja a un usuario de la base de datos
        
       Optional <Editorial> optional = editorialRepositorio.findById(id); // con el optional puedo ver si me devuelve un objeto en este caso un libro entonces lo busca y lo borra, sino devuelve una excepcion
        
        if (optional.isPresent()){
            Editorial editorial= optional.get();
            editorial.setAlta(Boolean.TRUE);
            editorialRepositorio.save(editorial);
        }else{
            throw new ExcepcionPropia ("El autor que se quiere habilitar no existe");
        }
    }
}
