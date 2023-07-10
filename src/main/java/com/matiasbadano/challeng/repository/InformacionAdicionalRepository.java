package com.matiasbadano.challeng.repository;

import com.matiasbadano.challeng.models.InformacionAdicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformacionAdicionalRepository extends JpaRepository<InformacionAdicional, Integer> {

    InformacionAdicional findByAlumnoId(int alumnoId);

}