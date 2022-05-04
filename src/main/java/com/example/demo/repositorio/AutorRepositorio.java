
package com.example.demo.repositorio;

import com.example.demo.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String/*tipo de dato que es nuestro id*/> { // esto no es un clase sino una interfaz, que la heredamos como de un dao padre, que es jpaRepostory el cual tiene muchas clases que podemos aplicar en nuestro repositoy
    
    
    //en las querys si yo se que la consulta me va a dar un solo resultado puedo usar un objeto, sino usamos una LIST, aunque no va a saltar error si el resultado es uno solo y elegimos LIST
    // ejemplo si queremos consultar un email y sabemos que este es unico, porque asi lo declaramos en el atributo, entonces podemos poner en la query un objeto y no una LIST
 
    @Query("SELECT a FROM Autor a WHERE a.id= :PARAMETRO")
    public List <Autor> buscarAutorporId (@Param("PARAMETRO") String id);
    
    
}
