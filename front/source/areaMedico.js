document.addEventListener("DOMContentLoaded", async () => {
  // Parte real (API) – deixamos comentado por enquanto
  /*
  const token = localStorage.getItem("token");
  if (!token) {
    alert("Você precisa estar logado para acessar esta área.");
    window.location.href = "login.html";
    return;
  }

  try {
    const response = await fetch("http://localhost:8080/api/consultas/futuras", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error("Erro ao carregar consultas.");
    }

    const consultas = await response.json();
    renderizarConsultas(consultas);
  } catch (error) {
    console.error("Erro ao buscar consultas:", error);
    alert("Erro ao carregar dados. Tente novamente mais tarde.");
  }
  */

  // 🔁 Parte simulada para testes sem back-end
  try {
    const response = await fetch("source/usuarios.json");
    const usuarios = await response.json();

    // Simula que o usuário logado é o médico com CRM "123456"
    const medico = usuarios.find((u) => u.tipo === "medico" && u.crm === "123456");

    if (!medico) {
      alert("Você precisa estar logado como médico para acessar esta área.");
      window.location.href = "login.html";
      return;
    }

    const consultasMock = [
      {
        paciente: "Maria Oliveira",
        dataHora: "2025-06-04T09:00:00",
        medico: medico.nome || "Dr. João Silva",
      },
      {
        paciente: "Carlos Souza",
        dataHora: "2025-06-04T10:30:00",
        medico: medico.nome || "Dr. João Silva",
      },
    ];

    renderizarConsultas(consultasMock);
  } catch (err) {
    console.error("Erro ao carregar dados simulados:", err);
    alert("Erro ao carregar informações locais.");
  }
});

function renderizarConsultas(consultas) {
  const container = document.getElementById("lista-consultas");
  container.innerHTML = "";

  consultas.forEach((consulta) => {
    const dataHora = new Date(consulta.dataHora);
    const horaFormatada = dataHora.toLocaleTimeString("pt-BR", {
      hour: "2-digit",
      minute: "2-digit",
    });

    const item = document.createElement("div");
    item.className = "px-6 py-4 flex items-center hover:bg-gray-50";
    item.innerHTML = `
      <div class="h-10 w-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-500">
        <i class="fas fa-user"></i>
      </div>
      <div class="ml-4 flex-1">
        <div class="flex items-center justify-between">
          <p class="text-sm font-medium text-gray-800">${consulta.paciente}</p>
          <p class="text-xs text-gray-500">${horaFormatada}</p>
        </div>
        <p class="text-xs text-gray-500 mt-1">Consulta com ${consulta.medico}</p>
      </div>
    `;
    container.appendChild(item);
  });
}
