package pleilist.app.domain.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import pleilist.app.domain.Configuration;
import pleilist.app.domain.Video;
import pleilist.app.domain.strategies.Strategy;

public class StrategyFactory {
	
	private static final String STRATEGIES_KEY = "strategies";

	private Strategy defaultStrategy = new Strategy() {
		
		@Override
		public List<Video> getVideos(int numVideos) {
			return new ArrayList<>();//por default eh devolvida uma lista vazia
		}
		
	};

	private Map<String, Strategy> mapeamento = new HashMap<>();

	private static StrategyFactory INSTANCE = null;

	public static StrategyFactory getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new StrategyFactory();
		}
		return INSTANCE;
	}

	private StrategyFactory() {
		Configuration conf = Configuration.getInstance();
		for (String name: conf.getStringList(STRATEGIES_KEY)) {
			Strategy strat = conf.<Strategy>createInstance(name, this.defaultStrategy);
			this.mapeamento.put(name, strat);
		}
	}

	public Strategy getStrategy(String name) {
		Optional<Strategy> maybeStrat = Optional.ofNullable(this.mapeamento.get(name));
		return maybeStrat.orElse(this.defaultStrategy);
	}
}
