public abstract class BazaKoduDoTestowania {
    protected int liczbaIteracji;

    /**
     * @return the liczbaIteracji
     */
    public int getLiczbaIteracji() {
        return liczbaIteracji;
    }

    /**
     * @param liczbaIteracji the liczbaIteracji to set
     */
    public void setLiczbaIteracji(int liczbaIteracji) {
        this.liczbaIteracji = liczbaIteracji;
    }

    public abstract void run();
}
