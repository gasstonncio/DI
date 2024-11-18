import tkinter as tk
from tkinter import simpledialog
from functools import partial

class MainMenu:
    def __init__(self, root, start_game_callback, show_stats_callback, quit_callback):
        self.root = root
        self.start_game_callback = start_game_callback
        self.show_stats_callback = show_stats_callback
        self.quit_callback = quit_callback
        self.menu_window = tk.Frame(root)
        self.menu_window.pack()

        self.play_button = tk.Button(self.menu_window, text="Jugar", command=self.start_game)
        self.play_button.pack()

        self.stats_button = tk.Button(self.menu_window, text="Estadísticas", command=self.show_stats)
        self.stats_button.pack()

        self.quit_button = tk.Button(self.menu_window, text="Salir", command=self.quit)
        self.quit_button.pack()

    def start_game(self):
        player_name = simpledialog.askstring("Nombre del jugador", "¿Cuál es tu nombre?")
        difficulty = simpledialog.askstring("Dificultad", "Elige la dificultad (facil, medio, dificil):")
        self.start_game_callback(player_name, difficulty)

    def show_stats(self):
        self.show_stats_callback()

    def quit(self):
        self.quit_callback()

class GameView:
    def __init__(self, on_card_click, update_move_count, update_time_callback, recursos):
        self.on_card_click = on_card_click
        self.update_move_count = update_move_count
        self.update_time_callback = update_time_callback
        self.recursos = recursos
        self.game_model = None  # Esto se inicializa luego
        self.window = None  # El contenedor principal de la ventana
        self.board_buttons = []  # Para manejar las cartas (botones)
        self.create_window()

    def create_window(self):
        """Configura la ventana inicial y los elementos como los contadores de tiempo y movimientos."""
        self.window = tk.Tk()
        self.window.title("Juego de Memoria")

        self.move_label = tk.Label(self.window, text="Movimientos: 0")
        self.move_label.pack()

        self.time_label = tk.Label(self.window, text="Tiempo: 0")
        self.time_label.pack()

        self.create_board()

    def create_board(self):
        """Genera el tablero de juego visual usando las imágenes de las cartas."""
        rows = len(self.game_model.board)  # Se usa self.game_model.board
        cols = len(self.game_model.board[0]) if rows > 0 else 0

        self.board_buttons = []

        for i in range(rows):
            row_buttons = []
            for j in range(cols):
                # Guardamos la referencia a la imagen para la carta actual
                card_image = self.recursos.get_card_image(self.game_model.board[i][j])  # Usamos self.game_model.board

                # Usamos partial para evitar problemas con la captura de i y j
                button = tk.Button(self.window, image=card_image, command=partial(self.on_card_click, i, j))
                button.grid(row=i, column=j)
                row_buttons.append(button)

            self.board_buttons.append(row_buttons)

    def update_board(self):
        """Actualiza el tablero después de una jugada."""
        for i, row in enumerate(self.game_model.board):
            for j, card_value in enumerate(row):
                card_image = self.recursos.get_card_image(card_value)
                self.board_buttons[i][j].config(image=card_image)

    def update_move_count(self, moves):
        """Actualiza el contador de movimientos."""
        self.move_label.config(text=f"Movimientos: {moves}")

    def update_time(self, time):
        """Actualiza el contador de tiempo."""
        self.time_label.config(text=f"Tiempo: {time}")

    def start(self):
        """Comienza el bucle de eventos de Tkinter."""
        self.window.mainloop()






