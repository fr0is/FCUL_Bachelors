package business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Esta classe tem o proposito de devolver uma data ficticia que representa a atual,
 * Os metodos desta classe devolvem sempre datas no dia 20 de Abril de 2020 as 14:00
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class MockDateTime {
	
	private MockDateTime() {}

	public static LocalDateTime currentDateTime() {
		return LocalDateTime.of(2020, 4, 20, 14, 00);
	}
	
	public static LocalDate currentDate() {
		return LocalDate.of(2020, 4, 20);
	}
	
	public static LocalTime currentTime() {
		return LocalTime.of(14, 00);
	}
}
