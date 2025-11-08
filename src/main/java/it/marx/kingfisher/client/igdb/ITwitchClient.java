package it.marx.kingfisher.client.igdb;

public interface ITwitchClient {

    /**
     * Restituisce il token di accesso corrente.
     * Se il token è scaduto, esegue automaticamente una nuova autenticazione.
     * 
     * @return il token di accesso valido
     */
    String getAccessToken();

    public String getClientId();
}
