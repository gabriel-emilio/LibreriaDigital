


package com.example.demo.repositorio;


import com.example.demo.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio  extends JpaRepository<Editorial, String/*tipo de dato que es nuestro id*/>{
    
@Query("SELECT a FROM Editorial a WHERE a.id= :PARAMETRO")
public List <Editorial> buscarEditorialporId (@Param("PARAMETRO") String id);    
    
}
