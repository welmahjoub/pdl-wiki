package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import utils.WikipediaVerif;

class WikipediaVerifTest {

	@Test
	void testUrlExist() throws IOException {
		assertTrue(WikipediaVerif.urlExist("https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Ido"),"Probl�me avec la fonction car cet url existe!");
		assertFalse(WikipediaVerif.urlExist("https://tththththththththhrh"),"Probl�me avec la fonction car cet url n'existe normalement pas!");
	}

	@Test
	void testIsArticleWikipedia() throws IOException {
		assertTrue(WikipediaVerif.isArticleWikipedia("https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Ido"),"Probl�me avec la fonction car ceci est un url wikipedia!");
		assertFalse(WikipediaVerif.isArticleWikipedia("https://www.openclassroom.fr"),"Probl�me avec la fonction car ceci n'est pas un url wikipedia!");
	}

}