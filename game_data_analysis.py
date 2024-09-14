import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from collections import OrderedDict

df = pd.read_csv("Video_Games.csv", encoding = 'UTF-8', sep=',')
video_pl = pd.DataFrame(df.groupby(['Platform']).size())
video_pl = video_pl[video_pl[0] > 1000]
video_pl

df_gb = pd.DataFrame(df.groupby(['Platform', 'Genre'])['Name'])
df_gb.rename(columns={0:'Platform, Genre'}, inplace=True)
li = df_gb['Platform, Genre'].tolist()

for i in range(len(li)):
  li[i] = list(li[i])

arr = np.array(li)

df_game = pd.DataFrame({
    'Platform' : arr[:, 0],
    'Genre' : arr[:, 1]})

li2 = df_gb[1].tolist()
for i in range(len(li2)):
  li2[i] = li2[i].tolist()

df_game['Name'] = li2
df_game.to_json('VideoGames.json', orient='records', indent=4)

data = pd.merge(left = df_game, right = video_pl, how = 'inner', on = 'Platform')
data = data.drop(0, axis=1)

df2 = df[['User_Score', 'Platform', 'Genre']].dropna()
df2['User_Score'] = pd.to_numeric(df['User_Score'], errors='coerce')
df2 = df2.groupby(['Platform', 'Genre']).mean().reset_index()
data2 = pd.merge(left = df2, right = video_pl, how = 'inner', on = 'Platform')
data2 = data2.drop(0, axis=1)

data = pd.merge(left = data, right = data2, how = 'left', on = ['Platform', 'Genre'])
data['User_Score'] = round(data['User_Score'], 1)
data.to_json('VideoGames.json', orient='records', indent=4)