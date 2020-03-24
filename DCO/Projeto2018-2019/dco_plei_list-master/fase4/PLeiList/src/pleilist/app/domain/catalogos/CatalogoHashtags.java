package pleilist.app.domain.catalogos;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import pleilist.app.domain.Hashtag;

public class CatalogoHashtags {

	private Map<String, Hashtag> hashtags;

	private CatalogoHashtags() {
		this.hashtags = new HashMap<>();
	}

	private static CatalogoHashtags INSTANCE = null;

	public static CatalogoHashtags getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new CatalogoHashtags();
		}
		return INSTANCE;
	}

	/**
	 * Encontra uma hashtag no catalogo de hashtags. Caso nao exista, cria e mete no catalogo.
	 * @param tag - o nome da hashtag
	 * @return a hashtag com nome tag
	 * @requires tag != null
	 */
	public Hashtag encontraHashtag(String tag) {
		Optional<Hashtag> talvezHashtag = Optional.ofNullable(this.hashtags.get(tag));//UC1-3f-1.1
		if(!talvezHashtag.isPresent()) {
			this.hashtags.put(tag, new Hashtag(tag));//UC1-3f-1.2 e UC1-3f-1.3
		}
		return talvezHashtag.orElse(this.hashtags.get(tag));
	}
	
	public Optional<Hashtag> getHashtag(String tag) {
		Optional<Hashtag> talvezHashtag = Optional.ofNullable(this.hashtags.get(tag));//UC2-2f-1.1 ou UC4-1f-1.1
		if(talvezHashtag.isPresent()) {
			return talvezHashtag;
		}
		return Optional.empty();
	}
	
}
