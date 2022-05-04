
package com.example.demo.repositorio;


import com.example.demo.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio  extends JpaRepository<Libro, String/*tipo de dato que es nuestro id*/>{
    
 @Query("SELECT a FROM Libro a WHERE a.id= :PARAMETRO")
public List <Libro> buscarLibroporId (@Param("PARAMETRO") String id);      
    
}
