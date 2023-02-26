package com.viksy.mobilele.init;

import com.viksy.mobilele.model.entity.*;
import com.viksy.mobilele.model.entity.enums.CategoryEnum;
import com.viksy.mobilele.model.entity.enums.EngineEnum;
import com.viksy.mobilele.model.entity.enums.TransmissionEnum;
import com.viksy.mobilele.model.entity.enums.UserRoleEnum;
import com.viksy.mobilele.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class DBInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final OfferRepository offerRepository;

    public DBInit(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, BrandRepository brandRepository, ModelRepository modelRepository, OfferRepository offerRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.offerRepository = offerRepository;
    }


    @Override
    public void run(String... args) {
        if(brandRepository.count() == 0) {
            initRoles();
            initAdmin();
            initBrands();
            initOffers();
        }
    }

   private void initBrands() {
       BrandEntity ford = new BrandEntity().setName("Ford");

       ModelEntity fiesta = new ModelEntity();
       fiesta.setName("Fiesta")
               .setImageURL("https://hips.hearstapps.com/hmg-prod/images/2017-ford-fiesta-1557785069.jpg?crop=0.817xw:1.00xh;0,0&resize=640:*")
               .setStartYear(1976)
               .setCategory(CategoryEnum.CAR);

       ModelEntity escort = new ModelEntity();
       escort.setName("Escort")
               .setImageURL("https://upload.wikimedia.org/wikipedia/commons/3/39/Ford_Escort_RS2000_MkI.jpg")
               .setStartYear(1968)
               .setEndYear(2002)
               .setCategory(CategoryEnum.CAR);

       ford.setModels(List.of(fiesta, escort));
       fiesta.setBrand(ford);
       escort.setBrand(ford);

       brandRepository.save(ford);
   }
    private void initRoles() {
        UserRoleEntity user = new UserRoleEntity();
        user.setRole(UserRoleEnum.USER);

        UserRoleEntity admin = new UserRoleEntity();
        admin.setRole(UserRoleEnum.ADMIN);

        userRoleRepository.saveAll(List.of(user, admin));
    }
    private void initAdmin() {
        UserRoleEntity adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        UserEntity admin1 = new UserEntity();
        admin1.
                setFirstName("Admin").
                setLastName("Adminov").
                setUsername("admin").
                setPassword(passwordEncoder.encode("topsecret")).
                setVerified(true).
                setEmail("admin@admin.com").
                setRoles(Set.of(userRole, adminRole));

        userRepository.save(admin1);
    }

    private void initOffers() {
        OfferEntity offer1 = new OfferEntity();
        offer1
                .setModel(modelRepository.findById(1L).orElse(null))
                .setBrand(brandRepository.findById(1L).orElse(null))
                .setEngine(EngineEnum.GASOLINE)
                .setTransmission(TransmissionEnum.MANUAL)
                .setMileage(22500)
                .setPrice(14300)
                .setYear(2019)
                .setDescription("Used but is still good")
                .setSeller(userRepository.findByUsername("admin").orElse(null))
                .setImageUrl("https://hips.hearstapps.com/hmg-prod/images/2017-ford-fiesta-1557785069.jpg?crop=0.817xw:1.00xh;0,0&resize=640:*");

        OfferEntity offer2 = new OfferEntity();
        offer2
                .setModel(modelRepository.findById(2L).orElse(null))
                .setBrand(brandRepository.findById(1L).orElse(null))
                .setEngine(EngineEnum.GASOLINE)
                .setTransmission(TransmissionEnum.MANUAL)
                .setMileage(50000)
                .setPrice(8300)
                .setYear(2000)
                .setDescription("Very used")
                .setSeller(userRepository.findByUsername("admin").orElse(null))
                .setImageUrl("https://upload.wikimedia.org/wikipedia/commons/3/39/Ford_Escort_RS2000_MkI.jpg");

        offerRepository.saveAll(List.of(offer1, offer2));
    }
}
