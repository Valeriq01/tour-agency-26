package tourAgency.tour_agency.service.destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tourAgency.tour_agency.model.entity.destination.Destination;
import tourAgency.tour_agency.repository.destination.DestinationRepository;

import java.math.BigDecimal;

@Component
public class DestinationInit implements CommandLineRunner {

    private final DestinationRepository destinationRepository;

    @Autowired
    public DestinationInit(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (destinationRepository.count() > 0) {
            return;
        }

        destinationRepository.save(Destination.builder()
                .name("Paris")
                .country("France")
                .description("City of love, lights and art museums")
                .price(BigDecimal.valueOf(799))
                .days(3)
                .availableSpots(10)
                .imageUrl("/image/paris-louvre.jpg")
                .experiences("Eiffel Tower,Louvre Museum,Seine Cruise,Notre Dame")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Bali")
                .country("Indonesia")
                .description("Tropical island with beaches, temples and rice terraces")
                .price(BigDecimal.valueOf(1399))
                .days(6)
                .availableSpots(10)
                .imageUrl("/image/bali.jpg")
                .experiences("Uluwatu Temple,Tegallalang Rice Terraces,Seminyak Beach,Mount Batur Sunrise Hike")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Rome")
                .country("Italy")
                .description("Ancient city full of history and architecture")
                .price(BigDecimal.valueOf(699))
                .days(2)
                .availableSpots(8)
                .imageUrl("/image/rome.jpg")
                .experiences("Colosseum,Forum Romanum,Trevi Fountain,Spanish Steps")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Maldives")
                .country("Maldives")
                .description("Tropical paradise with crystal clear water")
                .price(BigDecimal.valueOf(1499))
                .days(5)
                .availableSpots(5)
                .imageUrl("/image/maldives.jpg")
                .experiences("Overwater Villas,Snorkeling Coral Reefs,Sandbank Picnic,Sunset Cruise")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Barcelona")
                .country("Spain")
                .description("Gaudi architecture and vibrant nightlife")
                .price(BigDecimal.valueOf(899))
                .days(4)
                .availableSpots(12)
                .imageUrl("/image/barcelona.jpg")
                .experiences("Sagrada Familia,Park Güell,La Rambla,Barceloneta Beach")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Dubai")
                .country("UAE")
                .description("Luxury city with futuristic skyline")
                .price(BigDecimal.valueOf(1299))
                .days(4)
                .availableSpots(15)
                .imageUrl("/image/dubai.jpg")
                .experiences("Burj Khalifa,Dubai Mall,Desert Safari,Palm Jumeirah")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Chicago")
                .country("USA")
                .description("Windy City with stunning skyline, lakefront and deep-dish pizza")
                .price(BigDecimal.valueOf(1099))
                .days(4)
                .availableSpots(16)
                .imageUrl("/image/chicago.jpg")
                .experiences("Millennium Park,Navy Pier,Willis Tower,Chicago Riverwalk")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Istanbul")
                .country("Turkey")
                .description("Where East meets West, rich history and vibrant culture")
                .price(BigDecimal.valueOf(899))
                .days(4)
                .availableSpots(18)
                .imageUrl("/image/istanbul.jpg")
                .experiences("Hagia Sophia,Blue Mosque,Grand Bazaar,Bosphorus Cruise")
                .build());

        destinationRepository.save(Destination.builder()
                .name("London")
                .country("UK")
                .description("Historical city with modern culture")
                .price(BigDecimal.valueOf(999))
                .days(4)
                .availableSpots(18)
                .imageUrl("/image/london.jpg")
                .experiences("Big Ben,Buckingham Palace,London Eye,Tower Bridge")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Prague")
                .country("Czech Republic")
                .description("Fairytale old town and castles")
                .price(BigDecimal.valueOf(599))
                .days(3)
                .availableSpots(14)
                .imageUrl("/image/prague.jpg")
                .experiences("Charles Bridge,Prague Castle,Old Town Square,Astronomical Clock")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Santorini")
                .country("Greece")
                .description("White houses and magical sunsets")
                .price(BigDecimal.valueOf(1099))
                .days(5)
                .availableSpots(7)
                .imageUrl("/image/santorini.jpg")
                .experiences("Oia Sunset,Red Beach,Fira Town,Caldera Cruise")
                .build());

        destinationRepository.save(Destination.builder()
                .name("Cappadocia")
                .country("Turkey")
                .description("Hot air balloons, fairy chimneys and magical landscapes")
                .price(BigDecimal.valueOf(849))
                .days(4)
                .availableSpots(12)
                .imageUrl("/image/cappadocia.jpg")
                .experiences("Hot Air Balloons,Goreme Open Air Museum,Underground Cities,Love Valley")
                .build());
    }

}