document.addEventListener("DOMContentLoaded", () => {
  const pacienteBtn = document.getElementById("pacienteBtn");
  const medicoBtn = document.getElementById("medicoBtn");
  const emailField = document.getElementById("emailField"); // DIV container para email
  const crmField = document.getElementById("crmField");     // DIV container para CRM
  const loginForm = document.getElementById("login-form");

  const emailInput = document.getElementById("email");     // Input de Email
  const crmInput = document.getElementById("crm");         // Input de CRM
  const passwordInput = document.getElementById("password"); // Input de Senha

  let currentUserType = "paciente"; // Tipo de usuário padrão

  const updateUserType = (type) => {
    currentUserType = type;
    const isPaciente = type === "paciente";

    // Alterna a visibilidade dos contêineres dos campos
    emailField.classList.toggle("hidden", !isPaciente);
    crmField.classList.toggle("hidden", isPaciente);

    // Habilita/desabilita os inputs e define o atributo 'required'
    if (isPaciente) {
      emailInput.disabled = false;
      emailInput.required = true;
      crmInput.disabled = true;
      crmInput.required = false;
      // crmInput.value = ""; // Opcional: Limpar o campo CRM ao mudar para paciente
    } else { // Médico
      emailInput.disabled = true;
      emailInput.required = false;
      // emailInput.value = ""; // Opcional: Limpar o campo email ao mudar para médico
      crmInput.disabled = false;
      crmInput.required = true;
    }

    // Atualiza os estilos dos botões de seleção de perfil
    pacienteBtn.classList.toggle("bg-blue-500", isPaciente);
    pacienteBtn.classList.toggle("text-white", isPaciente);
    pacienteBtn.classList.toggle("bg-gray-200", !isPaciente);
    pacienteBtn.classList.toggle("text-gray-700", !isPaciente);

    medicoBtn.classList.toggle("bg-blue-500", !isPaciente);
    medicoBtn.classList.toggle("text-white", !isPaciente);
    medicoBtn.classList.toggle("bg-gray-200", isPaciente);
    medicoBtn.classList.toggle("text-gray-700", isPaciente);
  };

  // Inicializa o estado do formulário baseado no tipo de usuário padrão
  // Isso garante que os atributos 'disabled' e 'required' estejam corretos no carregamento da página.
  updateUserType(currentUserType);

  pacienteBtn.addEventListener("click", () => updateUserType("paciente"));
  medicoBtn.addEventListener("click", () => updateUserType("medico"));

  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const password = passwordInput.value;
    const email = emailInput.value; // Valor será do input ativo (email ou CRM)
    const crm = crmInput.value;     // Valor será do input ativo (email ou CRM)

    // Validação básica do lado do cliente (o HTML5 'required' já faz a maior parte)
    // Se o campo relevante estiver vazio (apesar do 'required'), não prossegue.
    // Isso é uma dupla checagem, útil se 'required' falhar por algum motivo ou para feedback imediato.
    if (currentUserType === "paciente" && !email) {
        // O navegador deve tratar isso com o 'required', mas pode adicionar um alerta se desejar.
        // alert("Por favor, insira seu e-mail.");
        return;
    }
    if (currentUserType === "medico" && !crm) {
        // alert("Por favor, insira seu CRM.");
        return;
    }
    if (!password) { // Senha é sempre obrigatória
        // alert("Por favor, insira sua senha.");
        return;
    }

    try {
      const response = await fetch("source/usuarios.json");
      if (!response.ok) {
        // Se o arquivo JSON não for encontrado ou houver outro erro HTTP
        throw new Error(`Erro ao carregar usuários: ${response.status} ${response.statusText}`);
      }
      const usuarios = await response.json();

      let usuarioValido = null;

      if (currentUserType === "paciente") {
        usuarioValido = usuarios.find(
          (u) => u.tipo === "paciente" && u.email === email && u.senha === password
        );
      } else if (currentUserType === "medico") {
        usuarioValido = usuarios.find(
          (u) => u.tipo === "medico" && u.crm === crm && u.senha === password
        );
      }

      if (usuarioValido) {
        alert("Login realizado com sucesso!");
        // Você pode querer armazenar informações do usuário no localStorage/sessionStorage aqui
        // Ex: localStorage.setItem("currentUser", JSON.stringify(usuarioValido));
        const destino = currentUserType === "paciente" ? "areaPaciente.html" : "areaMedico.html";
        window.location.href = destino;
      } else {
        alert("Credenciais inválidas.");
      }
    } catch (err) {
      console.error("Erro ao carregar ou processar usuários:", err);
      alert(`Erro ao validar login: ${err.message}. Verifique o console para mais detalhes.`);
    }
  });
});