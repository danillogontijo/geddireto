package br.org.ged.direto.model.service.impl;

import java.util.List;
import java.util.Locale;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.UsuarioRepository;
import br.org.ged.direto.model.service.UsuarioService;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("usuarioService")
@RemoteProxy(name = "usuarioJS")
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository usuarioRepository;
	private MessageSource messageSource;

	@Autowired
	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Usuario> getAll(Usuario usuario) {
		return this.usuarioRepository.getAll(usuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Usuario usuario) throws Exception {
		try {
			this.usuarioRepository.save(usuario);
		} catch (Exception e) {
			throw new Exception("Não foi possível salvar o usuario."
					+ e.getMessage());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Usuario selectById(Integer idUsuario) {
		return this.usuarioRepository.selectById(idUsuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Usuario selectByLogin(String usuLogin) {
		return this.usuarioRepository.selectByLogin(usuLogin);
	}

	@Override
	@RemoteMethod
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean validateUser(String usuLogin) {
		
		Object o = (Usuario) this.usuarioRepository.selectByLogin(usuLogin);
		
		return (o != null ? true : false);
	}

	@Override
	@RemoteMethod
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DataUtils> listActivedContas(String usuLogin) {
		return (this.usuarioRepository.listActivedContas(usuLogin));
	}

	/*public String getValidationMessage(Errors errors, String fieldName) {
		String message = "";

		FieldError fieldError = errors.getFieldError(fieldName);

		if (fieldError != null) {
			message = messageSource.getMessage(fieldError.getCode(), null,
					"This field is invalid", Locale.ENGLISH);
		}

		return message;
	}*/

}
