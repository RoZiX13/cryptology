package ciphers.simple;

public interface Cipher<E, D> {

    D decrypt(E string);

    E encrypt(D string);

}
