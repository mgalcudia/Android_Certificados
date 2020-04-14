package com.proyecto.Certificado.modelo;

import java.io.Serializable;

public class Certificados implements Serializable {

    private String idUser;

    @Override
    public String toString() {
        return  "\nTitulo Certificado: "+nombreCertificado+"\nEntidad emisora: "+entidadEmisora+
                "\nHoras Certificado: "+horasCertificado+"\nCreditos certificado "+creditosCertificado+
                "\nAño presentacion: "+anioCorte+"\n";
    }

    public String getIdCertificado() {
        return idCertificado;
    }

    public void setIdCertificado(String idCertificado) {
        this.idCertificado = idCertificado;
    }

    private  String idCertificado;
    private String nombreCertificado;
    private String entidadEmisora;
    private String horasCertificado;
    private String anioCorte;
    private String creditosCertificado;
    private String fechaFinCertificado;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNombreCertificado() {
        return nombreCertificado;
    }

    public void setNombreCertificado(String nombreCertificado) {
        this.nombreCertificado = nombreCertificado;
    }

    public String getEntidadEmisora() {
        return entidadEmisora;
    }

    public void setEntidadEmisora(String entidadEmisora) {
        this.entidadEmisora = entidadEmisora;
    }

    public String getHorasCertificado() {
        return horasCertificado;
    }

    public void setHorasCertificado(String horasCertificado) {
        this.horasCertificado = horasCertificado;
    }

    public String getAnioCorte() {
        return anioCorte;
    }

    public void setAnioCorte(String anioCorte) {
        this.anioCorte = anioCorte;
    }

    public String getCreditosCertificado() {
        return creditosCertificado;
    }

    public void setCreditosCertificado(String creditosCertificado) {
        this.creditosCertificado = creditosCertificado;
    }

    public String getFechaFinCertificado() {
        return fechaFinCertificado;
    }

    public void setFechaFinCertificado(String fechaFinCertificado) {
        this.fechaFinCertificado = fechaFinCertificado;
    }



    public Certificados() {
    }


}
