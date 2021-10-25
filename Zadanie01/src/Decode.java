class Decode extends DecoderInterface {
    /**
     * Wartosc ostatniego bita
     */
    private int lastBit = 0;
    /**
     * Wartosc X (wynosi 0 poki nie znajdziemy X w kodzie)
     */
    private int xValue = 0;
    /**
     * Ilosc odczytanych bitow
     */
    private int kodLiczby = 0;
    /**
     * Wartosc zakdowanej liczby
     */
    private int odczytanaLiczba = 0;
    /**
     * String z odczytanym kodem
     */
    String odczytanyKod = "";

    public void input(int bit) {
        if (lastBit == 1 && bit == 0) {
            if (xValue == 0) {
                xValue = kodLiczby;
            }
            odczytanaLiczba = (kodLiczby / xValue) - 1;
            odczytanyKod += odczytanaLiczba;
            kodLiczby = 0;
        }

        kodLiczby += bit;
        lastBit = bit;
    }

    public String output() {
        return odczytanyKod;
    }

    public void reset() {
        lastBit = 0;
        xValue = 0;
        kodLiczby = 0;
        odczytanaLiczba = 0;
        odczytanyKod = "";
    }
}
