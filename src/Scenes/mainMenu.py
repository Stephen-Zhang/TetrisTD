
import cocos, pyglet

from sys import exit
from cocos.director import *
from cocos.menu import *
from cocos.scene import *
from cocos.layer import *
from cocos.actions import *

class MainMenu(Menu):
	def __init__(self):
		super( MainMenu, self).__init__("Tetris TD")

		self.menu_valign = CENTER
		self.menu_halign = CENTER

		item1 = MenuItem('Start Game', self.on_quit)
		item2 = MenuItem('Options', self.on_quit)
		item3 = MenuItem('Exit', self.on_quit)

		item1.label.color = 255,0,0,1

		items = [
                        item1,
                        item2,
                        item3
		]

		selectAction = Blink(1, .2)

		self.create_menu( items, selectAction )

	def on_quit( self ):
		exit()

def main():

	pyglet.font.add_directory(".")

	director.init( resizable = True )
	director.run( Scene( MainMenu() ) )

if __name__ == '__main__':
	main()
