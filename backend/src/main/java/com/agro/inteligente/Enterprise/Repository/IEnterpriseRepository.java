package com.agro.inteligente.Enterprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IEnterpriseRepository extends JpaRepository<EnterpriseModelRepository, UUID> {
    @Query(value = "select count(*) > 0 from enterprise where cnpj = :cnpj", nativeQuery = true)
    boolean existByCnpj(String cnpj);
}
