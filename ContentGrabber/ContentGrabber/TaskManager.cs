using ContentGrabber.Parse;
using ContentGrabber.Write;
using System;
using System.Collections.Generic;
using System.Threading;

namespace ContentGrabber
{
    public class TaskManager
    {

        private Thread[] taskPool;
        private List<char> started;
        private object lockable = new object();

        public TaskManager(int taskCount)
        {
            taskPool = new Thread[taskCount];
            started = new List<char>();
            for (int i = 0; i < taskCount; i++)
            {
                taskPool[i] = new Thread(new ThreadStart(ParseArtitst));
            }
        }

        private void DoForThreads(Action<Thread> task)
        {
            if (task == null)
            {
                return;
            }
            lock (lockable)
            {
                for (int i = 0; i < taskPool.Length; i++)
                {
                    task(taskPool[i]);
                }
            }
        }

        private bool StartedLetter(char c)
        {
            lock (lockable)
            {
                return started.Contains(c);
            }
        }

        private bool AddStarted(char c)
        {
            lock (lockable)
            {
                if (started.Contains(c))
                {
                    return false;
                }
                started.Add(c);
                return true;
            }
        }

        public void Start()
        {
            DoForThreads((task) => {
                task.Start();
                Thread.Sleep(80);
            });
        }

        private void ParseArtitst()
        {
            int pageNum = 1;
            ArtistParser parser = new ArtistParser();
            foreach (char c in new Constants.Alphabet())
            {
                if (StartedLetter(c) || !AddStarted(c))
                {
                    continue;
                }
                while (true)
                {
                    string url = Constants.BASE_URL + c + "-" + pageNum + Constants.BASE_URL_SUFFIX;
                    Console.WriteLine("Artist Request for Category: " + (c + "-" + pageNum));
                    HtmlPage page = new HtmlPage(url, parser);
                    page.DoGet();
                    try
                    {
                        PageToFile pf = new PageToFile(new ArtistWriter());
                        string path = string.Format("grabs/__repo/a{0}.txt", pageNum);
                        PageToFile.CreatePathTo(path);
                        pf.Write(path, page);
                    }
                    catch
                    {
                        pageNum++;
                        continue;
                    }
                    foreach (KeyValuePair<string, string> pair in page.Items)
                    {
                        ParseSongs(pair.Key, pair.Value);
                    }
                    if (!page.HasNextPage)
                    {
                        break;
                    }
                    pageNum++;
                }
            }
        }

        private void ParseSongs(string artist, string url)
        {
            int pageNum = 1;
            char c = artist[0];
            while (true)
            {
                string newUrl = Constants.BuildArtistLink(url, pageNum);
                Console.WriteLine("Song Request for: " + artist);
                HtmlPage page = new HtmlPage(newUrl, new SongParser());
                page.DoGet();
                try
                {
                    string file = string.Format("grabs/{0}/{1}/__repo/{2}.txt", c, artist, pageNum);
                    PageToFile pf = new PageToFile(new SongWriter());
                    PageToFile.CreatePathTo(file);
                    pf.Write(file, page);
                }
                catch
                {
                    pageNum++;
                    continue;
                }
                foreach (KeyValuePair<string, string> pair in page.Items)
                {
                    ParseLyrics(pair.Key, pair.Value, c, artist);
                }
                if (!page.HasNextPage)
                {
                    break;
                }
                pageNum++;
            }
        }

        private void ParseLyrics(string song, string url, char letter, string artist)
        {
            Console.WriteLine("Lyric Request for: " + song);
            HtmlPage page = new HtmlPage(url, new LyricParser());
            page.DoGet();
            string file = string.Format("grabs/{0}/{1}/{2}/{3}.txt", letter, artist, page.Items["album"], song);
            try
            {
                //PageToFile pf = new PageToFile(new LyricWriter());
                PageToFile pf = new PageToFile(new DatabaseSongWriter());
                PageToFile.CreatePathTo(file);
                pf.Write(file, page);
            }
            catch { }
        }
    }
}
