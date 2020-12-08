

export function formatDate(d: Date): string {
    //const year: number = d.getFullYear()
    const year: number = 2020
    //const month: number = d.getMonth()
    const month: number = 12
    //const day: number = d.getDate()
    const day: number = 20

    return `${day}/${month}/${year}`;
}

export function isCallOpen(closingDate: Date): boolean{
    const todaysDate = new Date(1,1,1,1);
    return closingDate >= todaysDate
}