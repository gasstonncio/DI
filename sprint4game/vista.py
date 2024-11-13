import tkinter as tk
from tkinter import simpledialog, Toplevel

# Clase para la vista del tablero del juego
class GameView(tk.Toplevel):
    def __init__(self, on_card_click_callback, update_move_count_callback, update_time_callback):
        super().__init__()
        self.on_card_click_callback = on_card_click_callback
        self.update_move_count_callback = update_move_count_callback
        self.update_time_callback = update_time_callback
        self.labels = []

        # Frame principal para organizar la interfaz
        self.main_frame = tk.Frame(self)
        self.main_frame.pack(expand=True, fill="both")

        # Crear un Frame para el tablero
        self.board_frame = tk.Frame(self.main_frame)
        self.board_frame.grid(row=0, column=0, sticky="nsew")

        # Crear un Frame para los contadores
        self.status_frame = tk.Frame(self.main_frame)
        self.status_frame.grid(row=1, column=0, sticky="ew")

        # Etiquetas para los contadores de movimientos y tiempo
        self.move_label = tk.Label(self.status_frame, text="Movimientos: 0", font=("Arial", 12))
        self.move_label.pack(side="left", padx=10, pady=5)

        self.time_label = tk.Label(self.status_frame, text="Tiempo: 0", font=("Arial", 12))
        self.time_label.pack(side="right", padx=10, pady=5)

        # Configurar el redimensionamiento dinámico de las filas y columnas del layout
        self.main_frame.rowconfigure(0, weight=5)  # Tablero
        self.main_frame.rowconfigure(1, weight=1)  # Contadores
        self.main_frame.columnconfigure(0, weight=1)

    def create_board(self, model):
        """
        Crea el tablero del juego basado en el modelo y ajusta los elementos al tamaño de la ventana.
        """
        self.title("Juego de Memoria")

        # Obtener las dimensiones del tablero
        rows = len(model.board)
        cols = len(model.board[0]) if rows > 0 else 0

        # Crear etiquetas para las cartas y organizarlas en una cuadrícula dentro del frame del tablero
        self.labels = [[None for _ in range(cols)] for _ in range(rows)]
        for i in range(rows):
            for j in range(cols):
                # Configurar etiquetas sin `width` y `height` para que se redimensionen automáticamente
                label = tk.Label(self.board_frame, text="?", bg="blue", fg="white", font=("Arial", 14), bd=2, relief="ridge")
                label.grid(row=i, column=j, sticky="nsew")  # Hacer que ocupen todo el espacio de la celda

                # Enlazar el evento de clic con el callback proporcionado
                label.bind("<Button-1>", lambda event, x=i, y=j: self.on_card_click_callback(event, x, y))

                # Guardar la etiqueta en la lista
                self.labels[i][j] = label

        # Configurar redimensionamiento dinámico de filas y columnas en el frame del tablero
        for i in range(rows):
            self.board_frame.grid_rowconfigure(i, weight=1)
        for j in range(cols):
            self.board_frame.grid_columnconfigure(j, weight=1)

    def resize_board(self, event):
        """
        Método de ajuste para verificar redimensionado.
        """
        # Este método ahora puede permanecer vacío, ya que `grid` está manejando el tamaño de las etiquetas dinámicamente

    def update_board(self, pos, image_id):
        i, j = pos
        self.labels[i][j].config(text=image_id)

    def reset_cards(self, pos1, pos2):
        i1, j1 = pos1
        i2, j2 = pos2
        self.labels[i1][j1].config(text="?")
        self.labels[i2][j2].config(text="?")

    def update_move_count(self, moves):
        self.move_label.config(text=f"Movimientos: {moves}")

    def update_time(self, time):
        self.time_label.config(text=f"Tiempo: {time}")

    def destroy(self):
        self.main_frame.destroy()


# Clase para el menú principal
class MainMenu:
    def __init__(self, root, start_game_callback, show_stats_callback, quit_callback):
        self.root = root
        self.start_game_callback = start_game_callback
        self.show_stats_callback = show_stats_callback
        self.quit_callback = quit_callback

        # Ventana del menú
        self.menu_window = tk.Frame(root)
        self.menu_window.pack()

        # Botones
        self.play_button = tk.Button(self.menu_window, text="Jugar", command=self.start_game)
        self.play_button.pack()

        self.stats_button = tk.Button(self.menu_window, text="Estadísticas", command=self.show_stats)
        self.stats_button.pack()

        self.quit_button = tk.Button(self.menu_window, text="Salir", command=self.quit)
        self.quit_button.pack()

    def ask_player_name(self):
        # Solicitar el nombre del jugador
        return simpledialog.askstring("Nombre del jugador", "Por favor ingresa tu nombre:")

    def ask_difficulty(self):
        # Solicitar la dificultad
        return simpledialog.askstring("Dificultad", "Elige una dificultad (facil, medio, dificil):")

    def start_game(self):
        # Obtener nombre y dificultad
        player_name = self.ask_player_name()
        difficulty = self.ask_difficulty()

        # Ejecutar el callback de inicio de juego
        self.start_game_callback(player_name, difficulty)

    def show_stats(self):
        self.show_stats_callback()

    def quit(self):
        self.quit_callback()

