package com.silas.wsspring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.silas.wsspring.domain.Cidade;
import com.silas.wsspring.domain.Cliente;
import com.silas.wsspring.domain.Endereco;
import com.silas.wsspring.domain.enums.TipoCliente;
import com.silas.wsspring.dto.ClienteDTO;
import com.silas.wsspring.dto.ClienteNewDTO;
import com.silas.wsspring.repositories.CidadeRepository;
import com.silas.wsspring.repositories.ClienteRepository;
import com.silas.wsspring.repositories.EnderecoRepository;
import com.silas.wsspring.services.exceptions.DataIntegrityException;
import com.silas.wsspring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName() + ""));
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente cliNew = find(obj.getId());
		updateData(cliNew, obj);
		return repo.save(obj);
	}

	private void updateData(Cliente cliNew, Cliente obj) {
		cliNew.setNome(obj.getNome());
		cliNew.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		try {
			find(id);
			repo.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Cliente que possui entidades relacionada !");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO obj) {
		Cliente cli = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(), TipoCliente.toEnum(obj.getTipo()));
		Optional<Cidade> ci = cidadeRepository.findById(obj.getCidadeId()); 
		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), obj.getBairro(), obj.getCep(), cli, ci.orElse(null));
		cli.getEnderecos().add(end);
		cli.getTelefones().add(obj.getTelefone1());
		
		if(obj.getTelefone2() != null) {
			cli.getTelefones().add(obj.getTelefone2());
		}
		
		if(obj.getTelefone3() != null) {
			cli.getTelefones().add(obj.getTelefone3());
		}
		return cli;
	}
}
