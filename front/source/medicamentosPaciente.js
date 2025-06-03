document.addEventListener("DOMContentLoaded", () => {
  const medicamentos = [
    {
      nome: "Paracetamol 750mg",
      horario: "08:00 / 20:00",
      status: "Em uso",
      observacoes: "Tomar após refeições",
    },
    {
      nome: "Amoxicilina 500mg",
      horario: "12:00 / 22:00",
      status: "Finalizado",
      observacoes: "7 dias contínuos",
    },
  ];

  const container = document.getElementById("lista-medicamentos");

  medicamentos.forEach((med) => {
    const item = document.createElement("div");
    item.className = "bg-white rounded-xl shadow p-4 border-l-4 border-blue-400";

    item.innerHTML = `
      <h2 class="text-lg font-semibold text-gray-800">${med.nome}</h2>
      <p class="text-sm text-gray-500">Horários: ${med.horario}</p>
      <p class="text-sm text-gray-500">Status: ${med.status}</p>
      <p class="text-sm text-gray-500 italic">Obs: ${med.observacoes}</p>
    `;

    container.appendChild(item);
  });
});
