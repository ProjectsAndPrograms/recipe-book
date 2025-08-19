package com.oranbyte.recipebook.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oranbyte.recipebook.repository.UserRepository;

@Component
public class UserSeeder implements Seeder{
	
	@Autowired
	private UserRepository userRepo;
	
	public void seed() {
//		User admin = userRepo.findByEmail("admin@gmail.com");
//		if(admin == null) {
//			userRepo.save(User.builder().email("admin@gmail.com").name("Admin").image("adminpic.png").password("$2y$10$t1d8oT3yYF9.hrTvSpGTjeTtqvGsEhuVO.cA8fQaLupwtx53m.8J6").role("admin").build());
//		}
//		
//		User user = userRepo.findByEmail("user@gmail.com");
//		if(user == null) {
//			userRepo.save(User.builder().email("user@gmail.com").name("User").image("userpic.png").password("$2y$10$t1d8oT3yYF9.hrTvSpGTjeTtqvGsEhuVO.cA8fQaLupwtx53m.8J6").role("user").build());
//		}
	}
	
}
