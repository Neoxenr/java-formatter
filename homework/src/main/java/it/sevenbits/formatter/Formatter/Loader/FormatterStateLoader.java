package it.sevenbits.formatter.Formatter.Loader;

import au.com.bytecode.opencsv.CSVReader;
import it.sevenbits.formatter.Formatter.StateMap.Pair;
import it.sevenbits.formatter.Formatter.StateMap.State;
import it.sevenbits.formatter.Formatter.Token.IToken;
import it.sevenbits.formatter.Formatter.Token.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FormatterStateLoader implements ILoader<IToken, State> {
    private final Map<Pair<State, IToken>, State> stateMap;

    /**
     * stateMap initializing
     */
    public FormatterStateLoader() {
        stateMap = new HashMap<>();
    }

    /**
     * Loading .csv file and map initializing
     *
     * @throws IOException - stream's error
     */
    public void load() throws IOException {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new InputStreamReader(Objects.
                requireNonNull(getClass().getResourceAsStream("/config/formatterStateMap.csv")))), ',')) {
            List<String[]> strings = csvReader.readAll();
            for (int i = 1; i < strings.size(); i++) {
                for (int j = 1; j < strings.get(0).length; j++) {
                    if (strings.get(i)[j].length() > 0) {
                        stateMap.put(new Pair<>(new State(strings.get(i)[0]), new Token(strings.get(0)[j], "")),
                                new State(strings.get(i)[j]));
                    }
                }
            }
        }
    }

    /**
     * Returning map of transitions
     *
     * @return Map - map of transitions
     */
    public Map<Pair<State, IToken>, State> get() {
        return stateMap;
    }
}
