from vista import GameView, MainMenu
from modelo import GameModel

class GameController:
    def __init__(self, root, recursos):
        self.root = root
        self.recursos = recursos
        self.game_model = None
        self.game_view = None
        self.main_menu = MainMenu(root, self.start_game, self.show_stats, self.quit_game)

    def start_game(self, player_name, difficulty):
        # Iniciar el modelo de juego con el nombre del jugador, la dificultad y los recursos
        self.game_model = GameModel(difficulty, player_name, self.recursos)

        # Comenzar el temporizador
        self.game_model.start_timer()

        # Crear la vista del juego
        self.game_view = GameView(self.on_card_click, self.update_move_count, self.update_time_callback, self.recursos)

        # Crear el tablero
        self.game_view.create_board()

        # Ocultar el menú principal
        self.main_menu.menu_window.pack_forget()

        # Iniciar la actualización del temporizador
        self.update_time_callback()

    def on_card_click(self, event, x, y):
        # Lógica de selección de carta
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
        # Actualizar los movimientos en la vista
        self.game_view.update_move_count(moves)

    def update_time_callback(self):
        # Actualizar el tiempo transcurrido en la vista
        time_elapsed = self.game_model.get_time_elapsed()
        self.game_view.update_time(time_elapsed)

        # Llamar nuevamente a esta función después de 1000ms (1 segundo)
        self.root.after(1000, self.update_time_callback)

    def show_stats(self):
        # Mostrar las estadísticas
        stats = self.game_model.load_scores()
        self.game_view.destroy()  # Destruir la vista actual del juego
        self.main_menu.show_stats(stats)  # Mostrar estadísticas en el menú

    def quit_game(self):
        self.root.quit()