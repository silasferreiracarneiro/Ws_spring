package com.silas.wsspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.silas.wsspring.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	
}
