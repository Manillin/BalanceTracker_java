package Classi;

public class EsportatoreODS extends Esportatore{
    public EsportatoreODS(Bilancio b){super(b);}

    @Override
    public boolean esporta(String fileName) {
        return false;
    }
}
