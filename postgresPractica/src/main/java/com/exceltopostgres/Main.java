package com.exceltopostgres;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String excelFilePath = "/home/camper/Descargas/localidades.xlsx";
        String jdbcURL = "jdbc:postgresql://localhost:5432/practicapostgres";
        String username = "postgres";
        String password = "campus2023";
        try (FileInputStream inputStream = new FileInputStream(excelFilePath);
                Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
                String sql = "INSERT INTO localidades (codigo_departamento, nombre_departamento, codigo_municipio, nombre_municipio) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) { // Saltar la cabecera
                            continue;
                        }
                        Cell codigoDepartamentoCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        Cell nombreDepartamentoCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        Cell codigoMunicipioCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        Cell nombreMunicipioCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                        if (isCellEmpty(codigoDepartamentoCell) || isCellEmpty(nombreDepartamentoCell) ||
                                isCellEmpty(codigoMunicipioCell) || isCellEmpty(nombreMunicipioCell)) {
                            // Saltar filas con celdas vacías
                            continue;
                        }

                        int codigoDepartamento = getIntCellValue(codigoDepartamentoCell);
                        String nombreDepartamento = nombreDepartamentoCell.getStringCellValue().trim();
                        int codigoMunicipio = getIntCellValue(codigoMunicipioCell);
                        String nombreMunicipio = nombreMunicipioCell.getStringCellValue().trim();

                        statement.setInt(1, codigoDepartamento);
                        statement.setString(2, nombreDepartamento);
                        statement.setInt(3, codigoMunicipio);
                        statement.setString(4, nombreMunicipio);

                        statement.addBatch();
                    }
                    statement.executeBatch();
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir cadena a entero: " + e.getMessage());
        }
    }

    private static boolean isCellEmpty(Cell cell) {
        return cell == null || cell.getCellType() == CellType.BLANK;
    }

    private static int getIntCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return Integer.parseInt(cell.getStringCellValue().trim());
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            default:
                throw new IllegalStateException("Unexpected cell type: " + cell.getCellType());
        }
    }
}