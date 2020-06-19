package br.com.bank_app.control;


import br.com.bank_app.model.Credencial;

public class Bank {

    private static Bank instance;
    private Credencial credencial;

    /**
     * Configures Bank for use with the application, and creates the unique instance.
     *
     * @param credencial Bank user credentials.
     * @return The newly created unique instance
     */
    public static Bank iniciar(Credencial credencial) {
        instance = new Bank(credencial);
        return getInstance();
    }

    private Bank(Credencial credencial) {
        this.credencial = credencial;
    }

    /**
     * Returns the unique singleton instance of this session
     * @return
     */
    public static Bank getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Precisa inicializar o Bank.java antes de chamar este método");
        }
        return instance;
    }

    /**
     * Returns the current Credential object
     *
     * @return
     */
    public Credencial getCredencial() {
        return this.credencial;
    }

    /**
     * Clear Credential
     *
     */
    public void setCredencial(Credencial credencial) {
        this.credencial = credencial;
    }

}
