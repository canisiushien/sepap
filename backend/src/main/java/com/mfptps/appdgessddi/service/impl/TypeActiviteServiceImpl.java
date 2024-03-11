package com.mfptps.appdgessddi.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfptps.appdgessddi.entities.TypeActivites;
import com.mfptps.appdgessddi.repositories.TypeActiviteRepository;
import com.mfptps.appdgessddi.service.TypeActivitesService;
import com.mfptps.appdgessddi.service.dto.TypeActiviteDTO;
import com.mfptps.appdgessddi.service.mapper.TypeActiviteMapper;



@Service
@Transactional
public class TypeActiviteServiceImpl implements TypeActivitesService {
// 
	private final TypeActiviteRepository typeActiviteRepository;
	private final TypeActiviteMapper typeActiviteMapper;
	
	
	public TypeActiviteServiceImpl(TypeActiviteRepository typeActiviteRepository,
			TypeActiviteMapper typeActiviteMapper) {
		this.typeActiviteRepository = typeActiviteRepository;
		this.typeActiviteMapper = typeActiviteMapper;
	}
	
	public TypeActivites create(TypeActiviteDTO typeActivite) {
		TypeActivites type = typeActiviteMapper.toEntity(typeActivite);
        return typeActiviteRepository.save(type);
	}
//	@Override
//	public TypeActivites update(TypeActivites typeActivite) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public Optional<TypeActivites> get(String libelle) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public Page<TypeActivites> FindPage(Pageable pageable) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public void delete(Integer id) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	
//

	@Override
	public TypeActivites update(TypeActivites typeActivite) {
		 return typeActiviteRepository.save(typeActivite);
	}

	@Override
	 @Transactional(readOnly = true)
	public Optional<TypeActivites> get(Long id) {
		return typeActiviteRepository.findById(id);   
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TypeActivites> findPage(Pageable pageable) {
		 return typeActiviteRepository.findAll(pageable);

		
	}

	@Override
	public void delete(Long id) {
		 typeActiviteRepository.deleteById(id);
		
	}

	
}
