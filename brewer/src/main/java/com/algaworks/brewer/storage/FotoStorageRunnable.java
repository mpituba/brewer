package com.algaworks.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class FotoStorageRunnable implements Runnable {

	
	private MultipartFile[] files;
	private DeferredResult<String> resultado;
	
	//Contrutor usando campos
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<String> resultado) {
		this.files = files;
		this.resultado = resultado;
	}

	@Override
	public void run() {

		System.out.println(">>>>>File: " + files.length);
		System.out.println(">>>>>File: " + files[0].getSize());
				
		// TODO: Salvar a foto no sistema de arquivos...
		
		resultado.setResult("Ok, foto recebida!");
		
	}


}
