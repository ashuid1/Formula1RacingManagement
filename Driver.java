abstract public class Driver {
    private String name;
    private String team;
    private int racesParticipated;

    public Driver(String name, String team) {
        this.name = name;
        this.team = team;
        this.racesParticipated = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public int getRacesParticipated() {
        return racesParticipated;
    }

    public void participateInRace() {
        racesParticipated++;
    }

    // Abstract method to be implemented by subclasses
    public abstract void displayStatistics();

    public abstract void awardPoints(int position);


    // Formula1Driver class extending Driver
    static class Formula1Driver extends Driver {
        private int firstPositions;
        private int secondPositions;
        private int thirdPositions;
        private int totalPoints;

        public Formula1Driver(String name, String team) {
            super(name, team);
            this.firstPositions = 0;
            this.secondPositions = 0;
            this.thirdPositions = 0;
            this.totalPoints = 0;
        }

        public int getFirstPositions() {
            return firstPositions;
        }

        public int getSecondPositions() {
            return secondPositions;
        }

        public int getThirdPositions() {
            return thirdPositions;
        }

        public int getTotalPoints() {
            return totalPoints;
        }

        // Award points based on the finishing position
        public void awardPoints(int position) {
            if (position >= 1 && position <= 10) {
                switch (position) {
                    case 1:
                        totalPoints += 25;
                        firstPositions++;
                        break;
                    case 2:
                        totalPoints += 18;
                        secondPositions++;
                        break;
                    case 3:
                        totalPoints += 15;
                        thirdPositions++;
                        break;
                    case 4:
                        totalPoints += 12;
                        break;
                    case 5:
                        totalPoints += 10;
                        break;
                    case 6:
                        totalPoints += 8;
                        break;
                    case 7:
                        totalPoints += 6;
                        break;
                    case 8:
                        totalPoints += 4;
                        break;
                    case 9:
                        totalPoints += 2;
                        break;
                    case 10:
                        totalPoints += 1;
                        break;
                }
            }
        }

        // Display statistics for Formula1Driver
        @Override
        public void displayStatistics() {
            System.out.println("Driver: " + getName());
            System.out.println("Team: " + getTeam());
            System.out.println("Races Participated: " + getRacesParticipated());
            System.out.println("First Positions: " + getFirstPositions());
            System.out.println("Second Positions: " + getSecondPositions());
            System.out.println("Third Positions: " + getThirdPositions());
            System.out.println("Total Points: " + getTotalPoints());
        }
    }
}
