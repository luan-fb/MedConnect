// Espera o documento HTML ser totalmente carregado
document.addEventListener('DOMContentLoaded', function () {

  // --- SELEÇÃO DE ELEMENTOS DO HTML ---
  const pacienteBtn = document.getElementById('pacienteBtn');
  const medicoBtn = document.getElementById('medicoBtn');
  const loginForm = document.getElementById('login-form');

  const emailFieldContainer = document.getElementById('emailField'); // O div do campo email
  const crmFieldContainer = document.getElementById('crmField'); // O div do campo CRM
  const crmInput = document.getElementById('crm');
  const emailInput = document.getElementById('email');

  const userTypeInput = document.getElementById('userType');
  const registerLink = document.querySelector('a[href="cadastroPaciente.html"]');
  const errorMessage = document.createElement('p'); // Cria um parágrafo para mensagens de erro
  errorMessage.className = 'text-red-500 text-sm mt-4 text-center';
  loginForm.after(errorMessage); // Insere o parágrafo de erro depois do formulário

  // --- LÓGICA PARA OS BOTÕES DE PACIENTE/MÉDICO ---

  function setUserType(type) {
    userTypeInput.value = type; // Atualiza o valor do campo escondido

    if (type === 'paciente') {
      // Estilo do botão
      pacienteBtn.className = 'px-4 py-2 text-sm font-semibold rounded-md bg-blue-500 text-white hover:bg-blue-600';
      medicoBtn.className = 'px-4 py-2 text-sm font-semibold rounded-md bg-gray-200 text-gray-700 hover:bg-gray-300';

      // Lógica dos campos do formulário
      crmFieldContainer.classList.add('hidden'); // Esconde CRM
      crmInput.required = false; // CRM não é obrigatório
      emailFieldContainer.classList.remove('hidden'); // Garante que email está visível
      emailInput.required = true; // Email é obrigatório

      // Atualiza link de cadastro
      registerLink.href = 'cadastroPaciente.html';

    } else if (type === 'medico') {
      // Estilo do botão
      medicoBtn.className = 'px-4 py-2 text-sm font-semibold rounded-md bg-blue-500 text-white hover:bg-blue-600';
      pacienteBtn.className = 'px-4 py-2 text-sm font-semibold rounded-md bg-gray-200 text-gray-700 hover:bg-gray-300';

      // Lógica dos campos do formulário
      crmFieldContainer.classList.remove('hidden'); // Mostra CRM
      crmInput.required = true; // CRM é obrigatório
      emailFieldContainer.classList.add('hidden'); // Esconde Email
      emailInput.required = false; // Email não é obrigatório

      // Atualiza link de cadastro
      registerLink.href = 'cadastroMedico.html';
    }
  }

  // Adiciona os eventos de clique nos botões
  pacienteBtn.addEventListener('click', () => setUserType('paciente'));
  medicoBtn.addEventListener('click', () => setUserType('medico'));

  // --- LÓGICA PRINCIPAL DO FORMULÁRIO DE LOGIN ---

  loginForm.addEventListener('submit', function (event) {
    event.preventDefault(); // Previne o recarregamento da página
    errorMessage.textContent = ''; // Limpa erros antigos

    // IMPORTANTE: Substitua pelo IP público do seu servidor
    const backendUrl = 'http://137.131.251.170:8080';

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // O backend de login espera apenas email e senha
    const loginData = {
      email: email,
      password: password
    };

    // Requisição para o backend
    fetch(`${backendUrl}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(loginData)
    })
      .then(response => {
        if (!response.ok) {
          // Se a resposta for 401 ou 403, pega a mensagem de erro do corpo
          return response.text().then(text => { throw new Error(text || 'Credenciais inválidas') });
        }
        return response.json();
      })
      .then(data => {
        console.log('Login bem-sucedido:', data);
        localStorage.setItem('jwtToken', data.token);

        const decodedToken = decodeJwt(data.token);
        const userRole = decodedToken.role;

        // Redireciona com base na role vinda do token
        if (userRole === 'ROLE_MEDICO') {
          window.location.href = 'areaMedico.html';
        } else if (userRole === 'ROLE_PACIENTE') {
          window.location.href = 'areaPaciente.html';
        } else {
          errorMessage.textContent = 'Tipo de usuário desconhecido no token.';
        }
      })
      .catch(error => {
        console.error('Erro no login:', error);
        errorMessage.textContent = `Erro: ${error.message}`;
      });
  });

  // --- FUNÇÃO AUXILIAR PARA DECODIFICAR O JWT ---
  function decodeJwt(token) {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));
      return JSON.parse(jsonPayload);
    } catch (e) {
      return null;
    }
  }
});