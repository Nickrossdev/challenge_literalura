package com.alura.literalura;

import com.alura.literalura.app.App;
import com.alura.literalura.app.MenuPrincipal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.mostrarMenu();
	}
}
