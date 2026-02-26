package ro.zizicu.nwbase.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1403493962325498619L;

	public EntityNotFoundException(String s) {
		super(s);
	}
}
