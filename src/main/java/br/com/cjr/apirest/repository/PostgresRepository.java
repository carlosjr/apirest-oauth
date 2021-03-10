package br.com.cjr.apirest.repository;

public class PostgresRepository {

	public static final String cons = consultaNomeada();

	public static String consultaNomeada() {
		return "Select * from perfil";
	}
}
