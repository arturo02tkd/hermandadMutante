package com.mx.hermandad.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mx.hermandad.dao.ClsResDao;
import com.mx.hermandad.model.RegistroMutante;
import com.mx.hermandad.model.Verificasion;
import com.mx.hermandad.services.ClsResServices;
import com.mx.hermandad.utils.apiErros.ExceptionManager;

/**
 * hermandad de mutantes
 * 
 * @author Luis Arturo Aguilar Limas
 * @version 1.0
 * @sinse 12/06/2022
 */
@Service
public class ClsResServicesImpl implements ClsResServices {

	@Autowired
	private ClsResDao userDAO;

	private char[][] MatrizCuadrada;

	public void setArrayMatrizCuadrada(char[] array, int posicion) {
		this.MatrizCuadrada[posicion] = array;
	}

	@Override
	public Verificasion estadistica() {
		Verificasion verificasion= new Verificasion();
		List<RegistroMutante> listRegMutante =userDAO.findAll();
		if (listRegMutante != null || !listRegMutante.isEmpty()) {
			int mutante = 0;
			int humano = 0;
			for (int i = 0; i < listRegMutante.size(); i++) {
				if (listRegMutante.get(i).isHumano()) {
					humano++;
				} else if (!listRegMutante.get(i).isHumano()) {
					mutante++;
				}
			}
			double ratio = humano / mutante;
			verificasion.setCount_human_dna(humano);
			verificasion.setCount_mutant_dna(mutante);
			verificasion.setRatio(ratio);
		}
		return verificasion;
	}

	@SuppressWarnings("finally")
	@Override
	public boolean isMutant(String[] dna) {
		MatrizCuadrada = new char[dna.length][dna.length];
		int longitudMatriz = MatrizCuadrada.length;
		int contadorCadenasEncontradas = 0;

		if (!ValidarMatriz(dna)) {
			throw new ExceptionManager(HttpStatus.BAD_REQUEST, "Cadena de ADN invalida");
		}
		do {
			for (int x = 0; x < longitudMatriz; x++) {
				if (contadorCadenasEncontradas < 2) {
					for (int y = 0; y < longitudMatriz; y++) {
						char posicionActual = MatrizCuadrada[x][y];
						if (contadorCadenasEncontradas < 2) {
							if ((y + 4) <= longitudMatriz) {
								if (BuscaHorizontal(x, y, posicionActual)) {
									contadorCadenasEncontradas++;
									System.out.print(
											"Encontró cadena " + posicionActual + " en Horizontal:" + x + "," + y);
								}
							}
						} else {
							break;
						}

						if (contadorCadenasEncontradas < 2) {
							if ((x + 4) <= longitudMatriz) {
								if (BuscaVertical(x, y, posicionActual)) {
									contadorCadenasEncontradas++;
									System.out
											.print("Encontró cadena " + posicionActual + " en Vertical:" + x + "," + y);
								}
							}
						} else {
							break;
						}

						if (contadorCadenasEncontradas < 2) {
							if ((y + 4) <= longitudMatriz) {
								if ((x + 4) <= longitudMatriz) {
									if (BuscaDiagonal(x, y, posicionActual)) {
										contadorCadenasEncontradas++;
										System.out.print(
												"Encontró cadena " + posicionActual + " en Diagonal:" + x + "," + y);

									}
								}
							}
						} else {
							break;
						}
						if (contadorCadenasEncontradas < 2) {
							if (y >= 3) {
								if ((x + 4) <= longitudMatriz) {
									if (BuscaDiagonalInversa(x, y, posicionActual)) {
										contadorCadenasEncontradas++;
										System.out.print("Encontró cadena " + posicionActual + " en DiagonalInversa:"
												+ x + "," + y);

									}
								}
							}
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}
			break;
		} while (contadorCadenasEncontradas < 2);

		if (contadorCadenasEncontradas == 2) {
			try {

				RegistroMutante registro = new RegistroMutante();
				registro.setHumano(true);
				registro.setSecADN(concatenaSecuencia(dna));
				userDAO.save(registro);
				System.out.print("se guarda Mutante ");

			} catch (Exception ex) {
			} finally {
				return true;
			}

		} else {

			try {

				RegistroMutante registro = new RegistroMutante();
				registro.setHumano(false);
				registro.setSecADN(concatenaSecuencia(dna));
				userDAO.save(registro);
				System.out.print("se guarda Humano ");

			} catch (Exception ex) {
			} finally {
				return false;
			}
		}

	}

	public String concatenaSecuencia(String[] data) {
		StringBuffer bufer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			bufer.append(data[i]);
			if(!(i==data.length)) {
				bufer.append(",");
			}
		}
		return bufer.toString();
	}

	public boolean ValidarMatriz(String[] data) {
		@SuppressWarnings("unused")
		Boolean result = true;
		for (int i = 0; i < data.length; i++) {
			data[i].toUpperCase();
			for (int o = 0; o < data[i].length(); o++) {
				System.out.print(data[i].charAt(o));
				if (data[i].charAt(o) == '"' || data[i].charAt(o) == 'A' || data[i].charAt(o) == 'T'
						|| data[i].charAt(o) == 'C' || data[i].charAt(o) == 'G') {
					result = true;
				} else {
					return false;
				}
			}

			this.setArrayMatrizCuadrada(data[i].toUpperCase().toCharArray(), i);

		}
		return true;
	}

	private boolean BuscaHorizontal(int fila, int posicion, char posicionActual) {

		String cadena = "";
		boolean resultado = false;

		cadena = "" + posicionActual + MatrizCuadrada[fila][posicion + 1] + MatrizCuadrada[fila][posicion + 2]
				+ MatrizCuadrada[fila][posicion + 3];

		switch (cadena) {
		case "AAAA":
			resultado = true;
			break;
		case "TTTT":
			resultado = true;
			break;
		case "CCCC":
			resultado = true;
			break;
		case "GGGG":
			resultado = true;
			break;
		default:
			resultado = false;
			break;

		}

		return resultado;
	}

	private boolean BuscaVertical(int fila, int posicion, char posicionActual) {

		boolean resultado = false;
		String cadena = "";
		cadena = "" + posicionActual + MatrizCuadrada[fila + 1][posicion] + MatrizCuadrada[fila + 2][posicion]
				+ MatrizCuadrada[fila + 3][posicion];

		switch (cadena) {
		case "AAAA":
			resultado = true;
			break;
		case "TTTT":
			resultado = true;
			break;
		case "CCCC":
			resultado = true;
			break;
		case "GGGG":
			resultado = true;
			break;
		default:
			resultado = false;
			break;

		}

		return resultado;
	}

	private boolean BuscaDiagonal(int fila, int posicion, char posicionActual) {
		String cadena = "";
		cadena = "" + posicionActual + MatrizCuadrada[fila + 1][posicion + 1] + MatrizCuadrada[fila + 2][posicion + 2]
				+ MatrizCuadrada[fila + 3][posicion + 3];
		switch (cadena) {
		case "AAAA":
			return true;
		case "TTTT":
			return true;
		case "CCCC":
			return true;
		case "GGGG":
			return true;
		default:
			return false;

		}
	}

	private boolean BuscaDiagonalInversa(int fila, int posicion, char posicionActual) {
		String cadena = "";
		cadena = "" + posicionActual + MatrizCuadrada[fila + 1][posicion - 1] + MatrizCuadrada[fila + 2][posicion - 2]
				+ MatrizCuadrada[fila + 3][posicion - 3];
		switch (cadena) {
		case "AAAA":
			return true;
		case "TTTT":
			return true;
		case "CCCC":
			return true;
		case "GGGG":
			return true;
		default:
			return false;

		}
	}
}
