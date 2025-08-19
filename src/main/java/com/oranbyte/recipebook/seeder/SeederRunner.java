package com.oranbyte.recipebook.seeder;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class SeederRunner implements CommandLineRunner {

    private final List<Seeder> seeders;

    public SeederRunner(List<Seeder> seeders) {
        this.seeders = seeders;
    }

    @Override
    public void run(String... args) {
        for (Seeder seeder : seeders) {
            seeder.seed();
        }
    }
}
