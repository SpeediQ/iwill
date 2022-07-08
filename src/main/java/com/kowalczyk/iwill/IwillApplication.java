package com.kowalczyk.iwill;

import com.kowalczyk.iwill.model.Numerator;
import com.kowalczyk.iwill.model.Status;
import com.kowalczyk.iwill.repository.NumeratorRepository;
import com.kowalczyk.iwill.repository.StatusRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class IwillApplication {
    StatusRepository statusRepository;
    NumeratorRepository numeratorRepository;

    public IwillApplication(StatusRepository statusRepository, NumeratorRepository numeratorRepository) {
        this.statusRepository = statusRepository;
        this.numeratorRepository = numeratorRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(IwillApplication.class, args);
    }

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (statusRepository.findAll().size() == 0) {
            Status status = new Status();
            status.setName("Wizyta");
            status.setName("Rezerwacja");
            statusRepository.save(status);
        }
        if (numeratorRepository.findAll().size() == 0) {
            Numerator visit = new Numerator("Wizyta", "Numerator wskazuje pierwszy wolny NR dla Wizyty", 1, "W");
            Numerator client = new Numerator("Klient", "Numerator wskazuje pierwszy wolny NR dla Klienta", 1, "C");
            numeratorRepository.save(visit);
            numeratorRepository.save(client);
        }
    }

}
