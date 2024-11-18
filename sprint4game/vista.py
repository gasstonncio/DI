import tkinter as tk
from tkinter import simpledialog
from recursos import Recursos

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


class GameView(tk.Toplevel):
    def __init__(self, on_card_click_callback, update_move_count_callback, update_time_callback, recursos):
        super().__init__()
        self.on_card_click_callback = on_card_click_callback
        self.update_move_count_callback = update_move_count_callback
        self.update_time_callback = update_time_callback
        self.recursos = recursos
        self.labels = []

        self.main_frame = tk.Frame(self)
        self.main_frame.pack(expand=True, fill="both")

        self.board_frame = tk.Frame(self.main_frame)
        self.board_frame.grid(row=0, column=0, sticky="nsew")

        self.status_frame = tk.Frame(self.main_frame)
        self.status_frame.grid(row=1, column=0, sticky="ew")

        self.move_label = tk.Label(self.status_frame, text="Movimientos: 0", font=("Arial", 12))
        self.move_label.pack(side="left", padx=10, pady=5)

        self.time_label = tk.Label(self.status_frame, text="Tiempo: 0", font=("Arial", 12))
        self.time_label.pack(side="right", padx=10, pady=5)

        self.create_board()

    def create_board(self):
        rows = len(self.recursos.board)
        cols = len(self.recursos.board[0]) if rows > 0 else 0

        self.labels = [[None for _ in range(cols)] for _ in range(rows)]
        for i in range(rows):
            for j in range(cols):
                label = tk.Label(self.board_frame, image=self.recursos.get_card_back(), bd=2, relief="ridge")
                label.grid(row=i, column=j, sticky="nsew")
                label.bind("<Button-1>", lambda event, x=i, y=j: self.on_card_click_callback(event, x, y))
                self.labels[i][j] = label

        for i in range(rows):
            self.board_frame.rowconfigure(i, weight=1)
        for j in range(cols):
            self.board_frame.columnconfigure(j, weight=1)

    def update_board(self, position, card_value):
        i, j = position
        image = self.recursos.get_card_image(card_value)
        self.labels[i][j].config(image=image)

    def reset_cards(self, pos1, pos2):
        i1, j1 = pos1
        i2, j2 = pos2
        self.labels[i1][j1].config(image=self.recursos.get_card_back())
        self.labels[i2][j2].config(image=self.recursos.get_card_back())

    def update_move_count(self, moves):
        self.move_label.config(text=f"Movimientos: {moves}")

    def update_time(self, time):
        self.time_label.config(text=f"Tiempo: {time}")


