package com.algaworks.brewer.venda;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class TabelaItensVendaTest {
	
	private TabelaItensVenda tabelaItensVenda;
	
	/* Método que vai fazer o setup */
	@Before
	public void setUp() {
		this.tabelaItensVenda = new TabelaItensVenda();
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.TEN, tabelaItensVenda.getValorTotal());
	}
	
}
