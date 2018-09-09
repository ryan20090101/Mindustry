import sys
import discord

with open('token.txt', 'r') as infile:
    data = [i.strip() for i in infile.readlines()]
    TOKEN = data[0]
    BOT_CHANNEL = data[3]
  
client = discord.Client() 

def process_message(msg):
    '''
    first argument: kick/ban
    second argument: who is banned
    third argument: who did it
    fourth argument: explanation (if available)
    '''
    #msg = ['kick','badguy','desktop','testing']
    
    colours = {'kick':discord.Colour.orange(), 'ban':discord.Colour.red()}
    embed = discord.Embed(title=msg[0].upper(),
                          colour=colours[msg[0].lower()])
    embed.set_footer(text='You can contest or complain about your ban/kick in #moderation')
    embed.add_field(name='Player in question:', value=msg[1], inline=True)
    embed.add_field(name='Resposible admin:', value=msg[2], inline=True)
    if len(msg)>3:
        embed.add_field(name='REASON:', value=msg[3], inline=False)   

    return embed

@client.event
async def on_ready():
    print('bot is ready')
    channels = {i.name:i for i in client.get_all_channels()}
    try:
        embed = process_message(sys.argv[1:])
    
        await client.send_message(channels[BOT_CHANNEL], embed=embed)
    except Exception as e:
        print(e)
    finally:
        await client.close()
        await client.logout()
    print('done')

client.run(TOKEN)
