package com.silas.wsspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.silas.wsspring.domain.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

	
}
