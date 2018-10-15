package org.seoul.kk.service;

import org.seoul.kk.dto.traveler.RegisterTravelerDto;
import org.seoul.kk.entity.Traveler;
import org.seoul.kk.entity.constant.TravelProperty;
import org.seoul.kk.exception.NotFoundTraveler;
import org.seoul.kk.repository.TravelerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TravelerServiceImpl implements TravelerService {

    private TravelerRepository travelerRepository;

    public TravelerServiceImpl(TravelerRepository travelerRepository) {
        this.travelerRepository = travelerRepository;
    }

    @Override
    public Traveler getTravelerById(Long id) throws NotFoundTraveler {
        return travelerRepository.findById(id).orElseThrow(NotFoundTraveler::new);
    }

    @Override
    public Traveler getTravelerByUuid(String uuid) throws NotFoundTraveler {
        return travelerRepository.findByUuid(uuid).orElseThrow(NotFoundTraveler::new);
    }

    //cglib, jdk dynamic proxy problem or transaction distribute executed between repository.save and service method
    //found only save method doesn't rollback when break out unchecked exception. I don't know why.
    //found save method invoke from jdkDynamicAopProxy but register Traveler invoke cglib proxy. what the fuck this situation??
    @Transactional(readOnly = false)
    @Override
    public Traveler registerTraveler(RegisterTravelerDto requestBody, String uuid) {
        Traveler traveler = Traveler.builder()
                .uuid(uuid)
                .nickname(requestBody.getNickname())
                .property(TravelProperty.valueOf(requestBody.getProperty()))
                .build();

        travelerRepository.deleteById(1L);
        Traveler test = travelerRepository.findById(2L).get();
        test.setNickname("hello boy");
        travelerRepository.save(test);

        if (requestBody.getNickname().equals("dead")) {
            throw new RuntimeException();
        }
        Traveler response = travelerRepository.save(traveler);

        return response;
    }
}
