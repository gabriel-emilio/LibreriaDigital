
package com.example.demo.controladores;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Editorial;
import com.example.demo.entidades.Libro;
import com.example.demo.excepciones.ExcepcionPropia;
import com.example.demo.servicios.AutorServicio;
import com.example.demo.servicios.EditorialServicio;
import com.example.demo.servicios.LibroServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Portal_controlador {
    
    @Autowired
    private AutorServicio autorServicio; // importamos la clase de los servicios donde esta el metodo para registrar un autor
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @Autowired
    private LibroServicio libroServicio;
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
      @GetMapping("/rautor")
    public String rautor(){
        return "RegistroAutor.html";
    }
    
      @GetMapping("/rlibro")
    public String rlibro(ModelMap modelo){
        List<Autor> autores= autorServicio.mostrarTodos();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialServicio.mostrarTodos();
        modelo.put("editoriales", editoriales);
        return "registroLibro.html";
    }
    
      @GetMapping("/reditorial")
    public String reditorial(){
        return "RegistroEditorial.html";
    }
    
      @GetMapping("/lautor")
    public String lautor(ModelMap modelo /*esto agregamos para el thymeleaf*/){
        List<Autor> listaAutor= autorServicio.mostrarTodos();// esto tenemos que agregar para el thymeleaf, llamamos al metodo mostrar todos que es el que buscaba a todos los autores
        modelo.addAttribute("autores", listaAutor);// esto tenemos que agregar para el thymeleaf
        return "ListaAutores.html";  
    }
    
      @GetMapping("/leditorial")
    public String leditorial(ModelMap modelo){
        List<Editorial> listaEditorial= editorialServicio.mostrarTodos();
        modelo.addAttribute("editoriales",listaEditorial);
        return "ListarEditorial.html";
    }
    
       @GetMapping("/llibro")
    public String llibro(ModelMap modelo){
        
       List<Libro> listaLibros= libroServicio.mostrarTodos();
       modelo.addAttribute("libros",listaLibros);
        return "ListarLibro.html";
    }

    @PostMapping("/registroAutor")
    public String registroAutor(ModelMap modelo,@RequestParam String nombre){
       
        try {
            autorServicio.guardar(nombre, true);
            
        } catch (ExcepcionPropia ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            return "RegistroAutor.html";
        }
         return "index.html";
    }
    
     @PostMapping("/registroEditorial")
    public String registroEditorial(ModelMap modelo,@RequestParam String nombre){
       
        try {
            editorialServicio.guardar(nombre, true);
            
        } catch (ExcepcionPropia ex) {
             modelo.put("error", ex.getMessage());
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            return "RegistroEditorial.html";
        }
      
         return "index.html";
    }
    
    @PostMapping("/registroLibro") //para que salgan los errores de las variables long e integer  cuando se dejaban campos vacios en  el formulario no le puisimos el requestparam 
    public String registroLibro(ModelMap modelo, Long isbn, @RequestParam String titulo,@RequestParam String anio, Integer ejemplares, Integer ejemplaresPrestados,@RequestParam Autor autor,@RequestParam Editorial editorial ){
      
         
        try {
        //modelo.addAttribute("autor",autor);
        libroServicio.guardar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, true, autor, editorial); //ver que pasa que no atrapa los errores y no los muestra en pantalla como en el video
        
        
        } catch (ExcepcionPropia ex) {
        modelo.put("error", ex.getMessage());
        Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
        modelo.put("isbn", isbn);    
        modelo.put("titulo", titulo);
        modelo.put("anio", anio);
        modelo.put("ejemplares", ejemplares);
        modelo.put("ejemplaresPrestados", ejemplaresPrestados);
         List<Autor> autores= autorServicio.mostrarTodos();
        modelo.put("autores", autores);
        modelo.put("autor", autor);
        List<Editorial> editoriales = editorialServicio.mostrarTodos();
        modelo.put("editoriales", editoriales);
        modelo.put("editorial", editorial);
        
        return "registroLibro";        
    }
       return "index.html";

    }
    
     @GetMapping("/bajaeditorial/{id}")
    public String bajaeditorial(ModelMap modelo, @PathVariable String id){
        
        try{
            editorialServicio.deshabilitar(id);   
        }catch(ExcepcionPropia ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            
        }      
        return "redirect:/leditorial";
    }
    
    @GetMapping("/altaeditorial/{id}")
    public String altaeditorial(ModelMap modelo, @PathVariable String id){
        
        try{
            editorialServicio.habilitar(id);   
        }catch(ExcepcionPropia ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            
        }      
        return "redirect:/leditorial";
    }
    
     @GetMapping("/altaAutor/{id}")
    public String altaAutor(ModelMap modelo, @PathVariable String id){
        
        try{
            autorServicio.habilitar(id);   
        }catch(ExcepcionPropia ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            
        }      
        return "redirect:/lautor";
    }
    
     @GetMapping("/bajaAutor/{id}")
    public String bajaAutor(ModelMap modelo, @PathVariable String id){
        
        try{
            autorServicio.deshabilitar(id);   
        }catch(ExcepcionPropia ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            
        }      
        return "redirect:/lautor";
    }
    
    @GetMapping("/bajaLibro/{id}")
    public String bajaLibro(ModelMap modelo, @PathVariable String id){
        
        try{
            libroServicio.deshabilitar(id);   
        }catch(ExcepcionPropia ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            
        }      
        return "redirect:/llibro";
    }
    
     @GetMapping("/altaLibro/{id}")
    public String altaLibro(ModelMap modelo, @PathVariable String id){
        
        try{
            libroServicio.habilitar(id);   
        }catch(ExcepcionPropia ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            
        }      
        return "redirect:/llibro";
    }
    
    
    /*@GetMapping("/editar-autor")
    public String editarAutor(ModelMap modelo, @RequestParam String id )throws ExcepcionPropia{
         
        try{
            Autor autor =   autorServicio.consultar(id);
            modelo.addAttribute("editarAutor",autor);
        }catch (ExcepcionPropia ex){
            modelo.addAttribute("error",ex.getMessage());
        }
        return "editarAutor.html";
    }*/
    @GetMapping("/editar-autor/{id}")
    public String editarAutor(RedirectAttributes redirectAttributes, @PathVariable("id") String id, ModelMap modelo) {
        Autor autor;
        try {
   
        autor = autorServicio.consultar(id);
      
        modelo.put("nombre", autor.getNombre());
        return "editarAutor.html";
       

        } catch (ExcepcionPropia ex) {
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/lautor"; 
        }
       
    }
    
    
   /* @PostMapping("/actualizar-autor")
    public String registrar(ModelMap modelo,  String id, @RequestParam String nombre) throws ExcepcionPropia{
     Autor autor= null;
     try{
         autor= autorServicio.consultar(id);
         autorServicio.modificar(id, nombre);
         return "redirect:/index";
     } catch (ExcepcionPropia ex){
       modelo.put("error",ex.getMessage());
       modelo.put("nombreAutor",autor);  // este nombreAutor es el que ponemos en editarAutor
     }
     return "ListaAutores.html";
    }*/
    
  @PostMapping("/actualizar-autor")
    public String editamosAutor(RedirectAttributes redirectAttributes, ModelMap modelo, String id, @RequestParam(required = false) String nombreAutor) {
        Autor autor = autorServicio.buscarPorId(id).get();
        try {
            modelo.put("nombreAutor", autor.getNombre());
            autorServicio.modificar(id, nombreAutor);
        } catch (ExcepcionPropia ex) {
            
            modelo.put("error", ex.getMessage());
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/editar-autor/{id}";
        }
        modelo.put("mensaje", "Autor modificado con éxito");
        return "index.html";
    }
    
    
    //BOTON EDITAR PARA EDITORIAL
    
      @GetMapping("/editar-editorial/{id}")
       public String editarEditorial(RedirectAttributes redirectAttributes, @PathVariable("id") String id, ModelMap modelo) {
        Editorial editorial;
        try {
   
        editorial =editorialServicio.consultar(id);
      
        modelo.put("nombre", editorial.getNombre());
        return "editarEditorial.html";
       

        } catch (ExcepcionPropia ex) {
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/leditorial"; 
        }
       
    }
    
      
    
    @PostMapping("/actualizar-editorial")
     public String editamosEditorial(RedirectAttributes redirectAttributes, ModelMap modelo, String id, @RequestParam(required = false) String nuevoEditorial) {
        Editorial editorial = editorialServicio.buscarPorId(id).get();
        try {
            modelo.put("nuevoEditorial", editorial.getNombre());
            editorialServicio.modificar(id, nuevoEditorial);
        } catch (ExcepcionPropia ex) {
            
            modelo.put("error", ex.getMessage());
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/editar-editorial/{id}";
        }
        modelo.put("mensaje", "Editorial modificado con éxito");
        return "index.html";
    }
     
     @GetMapping("/editar-libro")
       public String editarLibro(@RequestParam String id, ModelMap modelo) {
        
        List<Autor> autores= autorServicio.mostrarTodos();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialServicio.mostrarTodos();
        modelo.put("editoriales", editoriales);
        
        try {
   
        Libro libro =libroServicio.consultar(id);
        modelo.addAttribute("nuevoLibro",libro); // con este como que traemos un libro, le pedimos que lo guarde y con los put accedemos a ciertos atributos del objeto
      
        /*modelo.put("anio", libro.getAnio());
        modelo.put("autor", libro.getAutor());
        modelo.put("editorial", libro.getEditorial());
        modelo.put("titulo", libro.getTitulo());
        modelo.put("isbn", libro.getIsbn());
        modelo.put("ejemplares", libro.getEjemplares());
        modelo.put("ejemplaresPrestados", libro.getEjemplaresPrestados());*/
        
        
        return "editarLibro.html";
       

        } catch (ExcepcionPropia ex) {
            Logger.getLogger(Portal_controlador.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/llibro"; 
        }
       
    }
       @PostMapping("/actualizar-libro")
     public String editamosLibro( ModelMap modelo,@RequestParam String id, @RequestParam(required = false) String titulo, @RequestParam (required = false) String anio, @RequestParam (required = false) Autor  autor, @RequestParam (required = false) Editorial editorial, Integer  ejemplares, Integer ejemplaresPrestados, Long isbn ) {
        
         
         Libro libro = null;
         
        
        try {
            
            libro=   libroServicio.consultar(id);
           /* modelo.put("nuevoIsbn", libro.getIsbn());
            modelo.put("nuevoTitulo", libro.getTitulo());
            modelo.put("nuevoEjemplares", libro.getEjemplares());
            modelo.put("nuevoAnio", libro.getAnio());
            modelo.put("nuevoEjemplaresPrestados", libro.getEjemplaresPrestados());
            modelo.put("nuevoAutor", libro.getAutor());
            modelo.put("nuevoEditorial", libro.getEditorial());*/
            libroServicio.modificar(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados,true, autor, editorial);
        } catch (ExcepcionPropia ex) {
            List<Autor> autores= autorServicio.mostrarTodos();
            modelo.put("autores", autores);
            List<Editorial> editoriales = editorialServicio.mostrarTodos();
            modelo.put("editoriales", editoriales);
            modelo.put("error",ex.getMessage());
            modelo.put("nuevoLibro",libro);
            
            
            return "redirect:/editar-libro";
        }
        modelo.put("mensaje", "Libro modificado con éxito");
        return "index.html";
    }
    
  
}
    

// para hacer funcionar vamos a templates y creamos una clase con html files
// luego tenemos que entrar en la pestañas superior en windows + navegator y luego tocamos el demo war que es nuestro proyecto
// seleccionamos el spring boot run y luego debemos poner en nuestro buscador
// http://localhost:8080/ y va a aparecer lo que pusimos en html

//SHORTCUT PARA REPETIR LINEAS CRTL+SHIFT+FLECHA ABAJO