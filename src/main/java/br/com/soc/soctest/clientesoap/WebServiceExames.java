package br.com.soc.soctest.clientesoap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface WebServiceExames {
	
	@WebMethod
	public String findExame(Long codigo);
	
	@WebMethod
	public String findAllByPacienteCodigo(Long codigo);
	
	
}
