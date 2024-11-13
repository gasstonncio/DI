import tkinter as tk
from vista import MainMenu, GameView
from modelo import GameModel


class GameController:
    def __init__(self, root):
        self.root = root
        self.game_model = None
        self.game_view = None

        #Crear el menú principal
        self.main_menu = MainMenu(root, self.start_game, self.show_stats, self.quit_game)

    def start_game(self, player_name, difficulty):
        #Iniciar el modelo de juego con el nombre del jugador y la dificultad
        self.game_model = GameModel(difficulty, player_name)

        #Crear la vista del juego
        self.game_view = GameView(self.on_card_click, self.update_move_count, self.update_time)

        #Crear el tablero
        self.game_view.create_board(self.game_model)

        #Comenzar el temporizador
        self.game_model.start_timer()

        #Ocultar el menú principal
        self.main_menu.menu_window.pack_forget()

    def on_card_click(self, event, x, y):
        #Lógica de selección de carta
        match = self.game_model.select_card(x, y)

        if match is not None:
            if match:
                self.game_view.update_board((x, y), self.game_model.board[x][y])
            else:
                self.game_view.reset_cards(self.game_model.selected_cards[0], self.game_model.selected_cards[1])

        self.update_move_count(self.game_model.moves)

        if self.game_model.is_game_complete():
            self.game_model.save_score()
            self.show_stats()

    def update_move_count(self, moves):
        #Actualizar los movimientos en la vista
        self.game_view.update_move_count(moves)

    def update_time(self, time):
        #Actualizar el tiempo en la vista
        self.game_view.update_time(time)

    def show_stats(self):
        #Mostrar las estadísticas
        stats = self.game_model.load_scores()
        self.game_view.destroy()
        self.main_menu.show_stats(stats)

    def quit_game(self):
        self.root.quit()


def main():
    root = tk.Tk()
    app = GameController(root)
    root.mainloop()


if __name__ == "__main__":
    main()
