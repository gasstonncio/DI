import tkinter as tk
from tkinter import simpledialog

class MainMenu:
    def __init__(self, root, start_game_callback, show_stats_callback, quit_callback):
        self.root = root
        self.start_game_callback = start_game_callback
        self.show_stats_callback = show_stats_callback
        self.quit_callback = quit_callback
        self.menu_window = tk.Frame(root)
        self.menu_window.pack(padx=10, pady=10)

        self.play_button = tk.Button(self.menu_window, text="Jugar", command=self.start_game)
        self.play_button.pack(pady=5)

        self.stats_button = tk.Button(self.menu_window, text="Estadísticas", command=self.show_stats)
        self.stats_button.pack(pady=5)

        self.quit_button = tk.Button(self.menu_window, text="Salir", command=self.quit)
        self.quit_button.pack(pady=5)

    def start_game(self):
        player_name = simpledialog.askstring("Nombre del jugador", "¿Cuál es tu nombre?")
        difficulty = simpledialog.askstring("Dificultad", "Elige la dificultad (fácil, medio, difícil):")
        self.start_game_callback(player_name, difficulty)

    def show_stats(self):
        self.show_stats_callback()

    def quit(self):
        self.quit_callback()


class GameView:
    def __init__(self, root, game_model, on_card_click, recursos):
        self.root = root
        self.game_model = game_model
        self.on_card_click = on_card_click
        self.recursos = recursos
        self.board_buttons = []
        self.timer_running = False

        self.create_window()

    def create_window(self):
        """Crea los elementos visuales."""
        self.frame = tk.Frame(self.root)
        self.frame.pack(padx=10, pady=10, fill=tk.BOTH, expand=True)

        self.move_label = tk.Label(self.frame, text="Movimientos: 0")
        self.move_label.pack(pady=5)

        self.time_label = tk.Label(self.frame, text="Tiempo: 0")
        self.time_label.pack(pady=5)

        self.create_board()

    def create_board(self):
        """Genera el tablero con botones representando las cartas."""
        board_frame = tk.Frame(self.frame)
        board_frame.pack(fill=tk.BOTH, expand=True)

        size = len(self.game_model.board)
        card_width = min(100, self.frame.winfo_width() // size)
        card_height = min(150, self.frame.winfo_height() // size)

        for i, row in enumerate(self.game_model.board):
            button_row = []
            for j, _ in enumerate(row):
                button = tk.Button(board_frame, image=self.recursos.get_back_image(),
                                   command=lambda x=i, y=j: self.on_card_click(x, y))
                button.grid(row=i, column=j, padx=5, pady=5, sticky="nsew")
                board_frame.rowconfigure(i, weight=1)  # Permitir redimensionar filas
                board_frame.columnconfigure(j, weight=1)  # Permitir redimensionar columnas
                button_row.append(button)
            self.board_buttons.append(button_row)

    def update_board(self):
        """Actualiza las imágenes de las cartas en el tablero."""
        for i, row in enumerate(self.game_model.board):
            for j, card_value in enumerate(row):
                if card_value:
                    image = self.recursos.get_card_image(card_value)
                    self.board_buttons[i][j].config(image=image, state="normal")
                else:
                    self.board_buttons[i][j].config(image=self.recursos.get_back_image(), state="normal")

    def reset_cards(self, cards):
        """Vuelve a mostrar el reverso de las cartas después de un fallo."""
        for x, y in cards:
            self.board_buttons[x][y].config(image=self.recursos.get_back_image())

    def update_move_count(self, moves):
        """Actualiza el contador de movimientos."""
        self.move_label.config(text=f"Movimientos: {moves}")

    def update_time(self, time):
        """Actualiza el contador de tiempo."""
        self.time_label.config(text=f"Tiempo: {time}")

    def start_timer(self):
        """Inicia el temporizador."""
        self.timer_running = True
        self.update_timer()

    def stop_timer(self):
        """Detiene el temporizador."""
        self.timer_running = False

    def update_timer(self):
        """Actualiza el temporizador cada segundo."""
        if self.timer_running:
            self.game_model.time_elapsed += 1
            self.update_time(self.game_model.time_elapsed)
            self.root.after(1000, self.update_timer)